package com.example.newsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.newsapp.R
import com.example.newsapp.base.BaseFragment
import com.example.newsapp.database.model.response.ArticlesItem
import com.example.newsapp.extensions.backPress
import com.example.newsapp.extensions.replaceFragment
import com.example.newsapp.extensions.showToastMsg
import com.example.newsapp.utils.load
import com.example.newsapp.viewmodels.DetailViewModel
import kotlinx.android.synthetic.main.fragment_details.*

/**
 * DetailsFragment.kt
 */
class DetailsFragment : BaseFragment(), View.OnClickListener, DetailViewModel.View {

    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var articlesItem: ArticlesItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        detailViewModel.attachView(this)
        mainActivity.updateNavigationViewVisibility(false)

        mainActivity.mainActivityViewModel.selectedArticleItem?.run {
            articlesItem = this
        } ?: mainActivity.backPress()

        articlesItem.apply {
            imgView.load(urlToImage)
            tvAuther.text = author
            tvPublishDate.text = publishedAt
            tvDetails.text = "$author\nc$content"
        }

        imgBack.setOnClickListener(this)
        imgShare.setOnClickListener(this)
        imgFavourite.setOnClickListener(this)
        btnNewsSource.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgBack -> mainActivity.backPress()
            R.id.imgShare -> {

            }
            R.id.btnNewsSource -> {
                replaceFragment(WebViewFragment(articlesItem.url!!))
            }
            R.id.imgFavourite -> {
                detailViewModel.saveNewsInDB(articlesItem)
            }
        }
    }

    override fun onUpdateUser(message: String) {
        showToastMsg(message)
    }

    override fun showProgressBar() {
        progressDialog.show()
    }

    override fun dismissProgressBar() {
        progressDialog.dismiss()
    }
}
