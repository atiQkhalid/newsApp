package com.example.newsapp.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
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
            it.getNewsArticles()
        }
        mainActivity.updateNavigationViewVisibility(true)
        onObserveNewsList()

        //SearchView Icons tint
        val searchIcon = searchViewHome.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.BLACK)
        val cancelIcon = searchViewHome.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.BLACK)

        //Initializing the searchView to filter teh data
        searchViewHome.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                homeViewModel.onSearchNews(newText)
                return false
            }
        })
    }

    //once we get the data from repo, populate it with the help of the adapter, NewsAdapter()
    private fun onObserveNewsList() {
        newsAdapter = NewsAdapter(this)

        homeViewModel.newsListData.observe(viewLifecycleOwner) {
            it?.let {
                newsAdapter?.setItems(it)
            }
        }

        newsAdapter.let {
            newsRecyclerView.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = it
            }
            it?.notifyDataSetChanged()
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
        mainActivity.storeSelectedArticle(note)
        replaceFragment(DetailsFragment())
    }
}
