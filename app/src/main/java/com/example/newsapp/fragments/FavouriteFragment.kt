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
import com.example.newsapp.viewmodels.FavouriteViewModel
import kotlinx.android.synthetic.main.fragment_favourite.*

/**
 * FavouriteFragment.kt
 */
class FavouriteFragment : BaseFragment(), FavouriteViewModel.View,
    NewsAdapter.NoteItemClickListener {
    private val favouriteViewModel: FavouriteViewModel by viewModels()
    private var newsAdapter: NewsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        favouriteViewModel.let {
            it.attachView(this)
            it.attachObserver(this)
            it.getNewsFromDB()
        }
        mainActivity.updateNavigationViewVisibility(true)
    }

    //once we get the data from repo, populate it with the help of the adapter, NewsAdapter()
    override fun onNewsFromDB(news: List<ArticlesItem>) {
        newsAdapter = NewsAdapter(this)
        newsAdapter?.setItems(news)
        newsAdapter.let {
            newsRecyclerView.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = it
            }
            it?.notifyDataSetChanged()
        }
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
        mainActivity.storeSelectedArticle(note)
        replaceFragment(DetailsFragment())
    }

    override fun onUpdateUser(message: String) {
        showToastMsg(message)
    }
}
