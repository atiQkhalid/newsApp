package com.example.newsapp.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.newsapp.base.BaseViewModel
import com.example.newsapp.database.model.response.ArticlesItem
import com.example.newsapp.database.model.response.NewsDao
import com.example.newsapp.utils.Const.API_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.coroutines.CoroutineContext

/**
 *  HomeViewModel.kt
 */
class HomeViewModel : BaseViewModel<HomeViewModel.View>(), CoroutineScope {

    private val query = MutableLiveData<String>()
    private val newsList = MutableLiveData<List<ArticlesItem>>()

    private val filteredData = Transformations.switchMap(query) { filterable ->
        Transformations.map(newsList) { list ->
            if (filterable.isNotBlank()) {
                list.filter {
                    it.title?.toLowerCase(Locale.getDefault())!!.contains(filterable)
                }
            } else
                list
        }
    }

    val newsListData = MediatorLiveData<List<ArticlesItem>>().apply {
        addSource(newsList) { value -> this.setValue(value) }
        addSource(filteredData) { value -> this.setValue(value) }
    }

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
                                this.articles?.let {
                                    newsList.value = it
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


    fun onSearchNews(query: String) {
        if (query.length >= QUERY_THRESHOLD || query.isEmpty()) {
            this.query.value = query.toLowerCase(Locale.getDefault()).trim()
        }
    }

    interface View {
        fun onUpdateUser(message: String)
        fun showProgressBar()
        fun dismissProgressBar()
    }

    companion object {
        const val QUERY_THRESHOLD = 2
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}