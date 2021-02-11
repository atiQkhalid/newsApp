package com.example.newsapp.viewmodels

import com.example.newsapp.base.BaseViewModel
import com.example.newsapp.database.model.News
import com.example.newsapp.database.model.response.ArticlesItem

/**
 *  HomeViewModel.kt
 */
class DetailViewModel : BaseViewModel<DetailViewModel.View>() {

    //Save note in DB using
    fun saveNewsInDB(articlesItem: ArticlesItem) {
        getView().showProgressBar()
        newsRepository.saveNews(
            News(
                title = articlesItem.title,
                description = articlesItem.description,
                content = articlesItem.content,
                urlToImage = articlesItem.urlToImage,
                url = articlesItem.url,
                author = articlesItem.author,
                publishedAt = articlesItem.publishedAt
            )
        )
        getView().onUpdateUser("saved successfully!")
        getView().dismissProgressBar()
    }


    interface View {
        fun onUpdateUser(message: String)
        fun showProgressBar()
        fun dismissProgressBar()
    }
}