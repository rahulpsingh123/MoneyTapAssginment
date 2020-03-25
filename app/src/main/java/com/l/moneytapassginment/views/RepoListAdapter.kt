package com.l.moneytapassginment.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.l.moneytapassginment.R
import com.l.moneytapassginment.database.GithubItemEntity
import com.l.moneytapassginment.helper.click
import com.l.moneytapassginment.helper.show
import io.realm.RealmList
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.repo_list_item.view.*


class RepoListAdapter(private val fragment : SearchRepoFragment) : RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {

    private var resultList: MutableList<GithubItemEntity> = mutableListOf()
    private val TYPE_LAST_ITEM = 999
    private val TYPE_ITEM = 0
    private val circleTransformation = RequestOptions.circleCropTransform()
        .error(R.drawable.ic_person_placeholder)
        .placeholder(R.drawable.ic_person_placeholder)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = when (viewType) {
            TYPE_ITEM -> {
                LayoutInflater.from(parent.context).inflate(R.layout.repo_list_item, parent, false)
            }

            TYPE_LAST_ITEM -> {
                LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            }

            else -> {
                //should not happen
                LayoutInflater.from(parent.context).inflate(R.layout.repo_list_item, parent, false)
            }
        }
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_ITEM -> {
                val githubItemEntity = resultList[position]
                val name = githubItemEntity.name
                val fullName = githubItemEntity.full_name
                val watcherCount = githubItemEntity.watchers_count
                val commitCount = githubItemEntity.forks_count
                val starCount = githubItemEntity.stargazers_count
                val ownerEntity = githubItemEntity.owner
                val url = ownerEntity?.avatar_url
                Glide.with(holder.itemView)
                    .asBitmap()
                    .load(url)
                    .apply(circleTransformation)
                    .into(holder.itemView.thumbnail)

                holder.itemView.tvName.text = name
                holder.itemView.tvFullName.text = fullName
                holder.itemView.tvWatcherCount.text = watcherCount.toString()
                holder.itemView.tvCommitCount.text = commitCount.toString()
                holder.itemView.tvStarCount.text = starCount.toString()

                holder.itemView.click {
                    (fragment.activity as? BaseActivity)?.addFragment(RepoDetailsFragment.newInstance(githubItemEntity.id), addToBackStack = true, transition = BaseActivity.TRANSITION.NONE)
                }
            }
            TYPE_LAST_ITEM -> {
                holder.itemView.progress_bar.show()
            }
        }
    }

    override fun getItemCount(): Int {
        return if (resultList.size == 0) 0 else resultList.size.plus(1)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            itemCount - 1 -> TYPE_LAST_ITEM
            else -> TYPE_ITEM
        }
    }


    fun setData(items: RealmList<GithubItemEntity>?) {
        resultList.addAll(items ?: mutableListOf())
        notifyDataSetChanged()
    }

    fun clear() {
        resultList.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)


}