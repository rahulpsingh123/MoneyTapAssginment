package com.l.moneytapassginment.viewModel

import com.l.moneytapassginment.database.GithubItemEntity
import com.l.moneytapassginment.database.GithubMainEntity
import com.l.moneytapassginment.responseModel.MoneyTapRepo
import com.l.moneytapassginment.responseModel.QueryAlreadyInProgress
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*

class MoneyTapViewModel : BaseViewModel() {
    private val repo = MoneyTapRepo()
    private var queryInProgress = false

    fun getGithubResultBaseOnSearchTerm(searchTerm: String): Single<GithubMainEntity>? {
        var entity = GithubMainEntity()
        return when {
            queryInProgress -> Single.error(QueryAlreadyInProgress())
            else -> {
                queryInProgress = true
                val queryMap: MutableMap<String?, Any?> = HashMap()
                queryMap["q"] = searchTerm
                queryMap["sort"] = "watcher_count"
                queryMap["order"] = "desc"
                queryMap["per_page"] = 10
                repo.getGitHubResult(queryMap)
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.map {
                        realm.executeTransaction { realm ->
                            entity = realm.createOrUpdateObjectFromJson(GithubMainEntity::class.java, it)
                        }
                        entity
                    }
                    ?.doFinally {
                        queryInProgress = false
                    }
            }
        }
    }

    fun getItemEntity(id : Int) : GithubItemEntity?{
        return realm.where(GithubItemEntity::class.java).equalTo("id", id).findFirst()
    }

    fun getItemEntity() : GithubMainEntity?{
        return realm.where(GithubMainEntity::class.java).findFirst()
    }


    override fun onCleared() {
        super.onCleared()
        repo.clear()
    }
}