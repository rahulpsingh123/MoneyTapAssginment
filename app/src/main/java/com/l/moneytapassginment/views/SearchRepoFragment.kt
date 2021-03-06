package com.l.moneytapassginment.views

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.l.moneytapassginment.R
import com.l.moneytapassginment.database.GithubItemEntity
import com.l.moneytapassginment.helper.gone
import com.l.moneytapassginment.helper.show
import com.l.moneytapassginment.responseModel.QueryAlreadyInProgress
import com.l.moneytapassginment.viewModel.MoneyTapViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.search_repo_fragment.*


class SearchRepoFragment : Fragment(), SearchView.OnQueryTextListener {
    private var page = 1
    private var query = ""
    private var model: MoneyTapViewModel? = null
    private val subscriptions = CompositeDisposable()
    private var resultList: MutableList<GithubItemEntity> = mutableListOf()

    private enum class STATE {
        LOADING, LOADED, ERROR, EMPTY
    }

    private var repoListAdapter: RepoListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        model = ViewModelProviders.of(activity!!).get(MoneyTapViewModel::class.java)
        return inflater.inflate(R.layout.search_repo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        val actionBar = (activity as AppCompatActivity?)?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)
        toolbar?.setTitle(R.string.search)

        setUpRecycleView()
        search?.setOnQueryTextListener(this)
        search?.isFocusable = false
        if (isInternetAvailable()) {
            searchFlickerImages("git", page)
        } else {
            setState(STATE.LOADED)
            repoListAdapter?.setData(model?.getItemEntity()?.items)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_search).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
            }

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onQueryTextSubmit(query: String): Boolean {
        view?.let {
            hideKeyBoard(it)
            if (query.isNotEmpty()) {
                searchFlickerImages(query, 1, true)
            }
        }
        return true
    }

    override fun onQueryTextChange(s: String): Boolean {
        return true
    }


    fun searchFlickerImages(query: String, page: Int = 1, isClearAdapter: Boolean = false) {
        if (page == 1) {
            setState(STATE.LOADING)
        }
        this.query = query
        model?.getGithubResultBaseOnSearchTerm(query)
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                setState(STATE.LOADED)
                if (isClearAdapter) {
                    resultList.clear()
                    repoListAdapter?.clear()
                }
                resultList.addAll(it.items ?: mutableListOf())
                if (it.items?.isEmpty() == true) setState(STATE.EMPTY)
                repoListAdapter?.setData(it.items)
            }, {
                when (it) {
                    is QueryAlreadyInProgress -> {
                    }
                    else -> {
                        setState(STATE.ERROR)
                    }
                }
            })
            ?.let { subscriptions.add(it) }
    }

    private fun setUpRecycleView() {
        recycler_view?.layoutManager = LinearLayoutManager(context)
        repoListAdapter = RepoListAdapter(this)
        recycler_view?.adapter = repoListAdapter
        recycler_view?.itemAnimator = DefaultItemAnimator()
        recycler_view?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(@NonNull recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val position =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (position >= resultList.size - 1) {
                    searchFlickerImages(query, page = ++page, isClearAdapter = false)
                }
            }
        })
    }

    private fun hideKeyBoard(view: View) {
        view.let {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun setState(state: STATE, message: String? = null) {
        when (state) {
            STATE.LOADING -> {
                progress_bar.show()
                tv_error_message.gone()
            }
            STATE.LOADED -> {
                progress_bar.gone()
                tv_error_message.gone()
            }
            STATE.ERROR -> {
                if (repoListAdapter?.itemCount == 0) {
                    tv_error_message.text = getString(R.string.no_network)
                } else {
                    Toast.makeText(activity, getString(R.string.no_network), Toast.LENGTH_LONG)
                        .show()
                }
                progress_bar.gone()
                tv_error_message.show()
            }
            STATE.EMPTY -> {
                if (repoListAdapter?.itemCount == 0) {
                    tv_error_message.text = getString(R.string.no_data_found)
                } else {
                    Toast.makeText(activity, getString(R.string.no_data_found), Toast.LENGTH_LONG)
                        .show()
                }
                tv_error_message.show()
                progress_bar.gone()
            }
        }
    }

    private fun isInternetAvailable(): Boolean {
        val conMgr = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val netInfo = conMgr?.activeNetworkInfo
        return netInfo?.isAvailable == true
    }


}