package com.example.newsapp.activities

import android.os.Bundle
import androidx.activity.viewModels
import com.example.newsapp.R
import com.example.newsapp.base.BaseActivity
import com.example.newsapp.database.model.response.ArticlesItem
import com.example.newsapp.extensions.gone
import com.example.newsapp.extensions.replaceFragmentSafely
import com.example.newsapp.extensions.visible
import com.example.newsapp.fragments.FavouriteFragment
import com.example.newsapp.fragments.HomeFragment
import com.example.newsapp.viewmodels.MainActivityViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * MainActivity.kt, Main activity class, launcher activity
 */
class MainActivity : BaseActivity(), MainActivityViewModel.View {

    val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        replaceFragmentSafely(HomeFragment())
    }

    fun storeSelectedArticle(articleItem : ArticlesItem){
        mainActivityViewModel.storeSelectedArticle(articleItem)
    }

    fun updateNavigationViewVisibility(visible: Boolean) {
        if (visible)
            navigationView.visible()
        else
            navigationView.gone()
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragmentSafely(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_favourite -> {
                    replaceFragmentSafely(FavouriteFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
}