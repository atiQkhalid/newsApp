package com.example.newsapp.viewmodels

import com.example.newsapp.base.BaseViewModel
import com.example.newsapp.database.model.response.ArticlesItem

/**
 * MainActivityViewModel.kt
 */
class MainActivityViewModel : BaseViewModel<MainActivityViewModel.View>() {

    var selectedArticleItem : ArticlesItem? = null

    fun storeSelectedArticle(articleItem : ArticlesItem){
        selectedArticleItem = articleItem
    }

    interface View
}