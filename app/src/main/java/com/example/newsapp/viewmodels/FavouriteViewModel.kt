package com.example.newsapp.viewmodels

import com.example.newsapp.base.BaseViewModel
import com.example.newsapp.database.model.News
import com.example.newsapp.database.model.response.ArticlesItem

/**
 * favouriteViewModel.kt
 */
class FavouriteViewModel : BaseViewModel<FavouriteViewModel.View>() {

    fun getNewsFromDB() {
        newsRepository.getNewsList()?.observe(getLifecycleOwner(), {
            if (it.isNotEmpty()) {
                getView().onNewsFromDB(mapperToDomain(it))
            } else {
                getView().onUpdateUser("No note found, create new!")
            }
        })
    }


    private fun mapperToDomain(news: List<News>): List<ArticlesItem> {
        return news.map {
            ArticlesItem(
                id = it.id,
                title = it.title,
                publishedAt = it.publishedAt,
                author = it.author,
                urlToImage = it.urlToImage,
                url = it.url,
                description = it.description,
                content = it.content
            )
        }
    }

    interface View {
        fun onNewsFromDB(news: List<ArticlesItem>)
        fun onUpdateUser(message: String)
        fun showProgressBar()
        fun dismissProgressBar()
    }
}