package com.l.moneytapassginment.views

import android.annotation.SuppressLint
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.example.githubsearchrepo.fragment.WebAppInterface
import com.l.moneytapassginment.R
import kotlinx.android.synthetic.main.project_detail_fragment.*

class WebViewFragment : Fragment() {

    var webUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.project_detail_fragment, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled", "AddJavascriptInterface")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // progress on player resize
        val layoutParams = news_load_progress?.layoutParams as LinearLayout.LayoutParams
        news_load_progress?.measure(0, 0)
        layoutParams.setMargins(0, -(news_load_progress?.measuredHeight?.div(2))!!, 0, 0)
        news_load_progress?.layoutParams = layoutParams
        news_load_progress.show()

        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        val actionBar = (activity as AppCompatActivity?)?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)


        webview?.settings?.builtInZoomControls = true
        webview?.settings?.displayZoomControls = false
        webview?.settings?.javaScriptEnabled = true
        webview?.addJavascriptInterface(WebAppInterface(activity!!), "android")
        webview?.webViewClient = WebViewClient()

        webview?.loadUrl(webUrl)
        webview?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                news_load_progress?.hide()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String?): WebViewFragment {
            val webViewFragment = WebViewFragment()
            webViewFragment.webUrl = url
            return webViewFragment
        }
    }
}