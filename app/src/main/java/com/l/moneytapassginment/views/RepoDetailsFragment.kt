package com.l.moneytapassginment.views

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.l.moneytapassginment.R
import com.l.moneytapassginment.database.GithubItemEntity
import com.l.moneytapassginment.helper.click
import com.l.moneytapassginment.viewModel.MoneyTapViewModel
import kotlinx.android.synthetic.main.repo_details_fragment.*

class RepoDetailsFragment : Fragment() {

    private var githubItemEntity: GithubItemEntity? = null
    private var model: MoneyTapViewModel? = null
    var id : Int? = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        model = ViewModelProviders.of(this).get(MoneyTapViewModel::class.java)
        return inflater.inflate(R.layout.repo_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity?)?.setSupportActionBar(tool_bar)
        val actionBar = (activity as AppCompatActivity?)?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)
        githubItemEntity = model?.getItemEntity(id ?: -1)

        val projectLink = githubItemEntity?.html_url
        val description = githubItemEntity?.description
        val ownerEntity = githubItemEntity?.owner
        val url = ownerEntity?.avatar_url
        val name = githubItemEntity?.name
        tool_bar.title = name

        Glide.with(activity!!)
            .asBitmap()
            .load(url)
            .apply(
                RequestOptions.circleCropTransform()
                    .placeholder(R.drawable.user_placeholder)
                    .error(R.drawable.user_placeholder))
            .into(img_contributor_avatar)

        val text = "<a href='http://www.google.com'>$projectLink</a>"
        tv_project_link.text = Html.fromHtml(text)
        tv_description.text = description

        tv_project_link.click{
            (activity as? BaseActivity)?.addFragment(WebViewFragment.newInstance(projectLink), addToBackStack = true, transition = BaseActivity.TRANSITION.NONE)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(id: Int): RepoDetailsFragment {
            val repoDetailsFragment = RepoDetailsFragment()
            repoDetailsFragment.id = id
            return repoDetailsFragment
        }
    }
}