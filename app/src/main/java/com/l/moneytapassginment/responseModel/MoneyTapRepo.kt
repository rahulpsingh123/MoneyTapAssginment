package com.l.moneytapassginment.responseModel

import com.l.moneytapassginment.network.APIManager
import com.l.moneytapassginment.network.NetworkClient
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MoneyTapRepo : BaseRepo() {

    fun getGitHubResult(searchTerm: MutableMap<String?, Any?>): Single<String>? {
        return APIManager.instance?.searchRequest(searchTerm)?.let {
            NetworkClient
                .getResult(it)
                .subscribeOn(Schedulers.io())
        }
    }
}