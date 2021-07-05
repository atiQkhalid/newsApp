package com.example.newsapp.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.newsapp.R
import com.example.newsapp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_web_view.*

/**
 * The PrivacyPolicesFragment.kt
 */

class WebViewFragment(private val url: String) : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startWebView(url)
    }

    private fun startWebView(url: String) {
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(
                view: WebView?,
                url: String?,
                favicon: Bitmap?
            ) {
                progressDialog.show()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressDialog.dismiss()
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progressDialog.dismiss()
            }
        }
        // Enable the javascript
        webView.settings.javaScriptEnabled = true
        // Render the web page
        webView.loadUrl(url)
    }
}
