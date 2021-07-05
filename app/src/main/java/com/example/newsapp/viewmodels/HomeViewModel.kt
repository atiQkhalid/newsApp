package com.example.newsapp.viewmodels

import com.example.newsapp.base.BaseViewModel
import com.example.newsapp.database.model.News
import com.example.newsapp.database.model.response.ArticlesItem
import com.example.newsapp.database.model.response.NewsDao
import com.example.newsapp.utils.Const.API_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *  HomeViewModel.kt
 */
class HomeViewModel : BaseViewModel<HomeViewModel.View>() {

    fun getNewsArticles() {
        getView().showProgressBar()
        newsRepository.getNewsArticles(keyword = "besiktas", pageNumber = "1", apiKey = API_KEY)
            .enqueue(object : Callback<NewsDao> {
                override fun onResponse(
                    call: Call<NewsDao>,
                    response: Response<NewsDao>
                ) {
                    getView().dismissProgressBar()
                    response.run {
                        if (isSuccessful) {
                            body()?.run {
                                mapperToDomain(this.articles)?.let { list ->
                                    list.forEach {
                                        newsRepository.saveNews(it)
                                    }
                                } ?: getView().onUpdateUser("Something went wrong")
                            } ?: getView().onUpdateUser("Something went wrong")
                        }
                    }
                }

                override fun onFailure(call: Call<NewsDao>, t: Throwable) {
                    getView().dismissProgressBar()
                    getView().onUpdateUser(t.message.toString())
                }
            })
    }

    fun getNewsList(news: (List<ArticlesItem>?) -> Unit) {
        getView().showProgressBar()
        news(domainToMapper(newsRepository.getNewsList()?.value))
        getView().dismissProgressBar()
    }

    private fun domainToMapper(news: List<News>?): List<ArticlesItem>? {
        return news?.map {
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

    private fun mapperToDomain(news: List<ArticlesItem>?): List<News>? {
        return news?.map {
            News(
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
        fun onUpdateUser(message: String)
        fun showProgressBar()
        fun dismissProgressBar()
    }
}