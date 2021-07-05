package com.example.newsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.base.BaseFragment
import com.example.newsapp.database.model.response.ArticlesItem
import com.example.newsapp.extensions.replaceFragment
import com.example.newsapp.extensions.showToastMsg
import com.example.newsapp.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * HomeFragment.kt
 */
class HomeFragment : BaseFragment(), HomeViewModel.View, NewsAdapter.NoteItemClickListener {
    private val homeViewModel: HomeViewModel by viewModels()
    private var newsAdapter: NewsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeViewModel.let {
            it.attachView(this)
            it.attachObserver(this)
            it.getNewsArticles()
        }

        swipeContainer.setOnRefreshListener {
            getNewsData()
        }

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        newsAdapter = NewsAdapter(this).also {
            newsRecyclerView.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = it
            }
        }
        getNewsData()
    }

    //once we get the data from repo, populate it with the help of the adapter, NewsAdapter()
    private fun getNewsData() {
        if (swipeContainer.isRefreshing) swipeContainer.setRefreshing(false)
        homeViewModel.getNewsList {
            it?.let {
                newsAdapter?.setItems(it)
            }
        }
    }

    //To update the user against any unusual situation
    override fun onUpdateUser(message: String) {
        showToastMsg(message)
    }

    //Show the progress while fetching date from repo
    override fun showProgressBar() {
        progressDialog.show()
    }

    //Hide the progress after fetching date from repo or in error case
    override fun dismissProgressBar() {
        progressDialog.dismiss()
    }

    //On note item click listener
    override fun onItemClickListener(note: ArticlesItem) {
        replaceFragment(WebViewFragment(note.url!!))
    }
}
