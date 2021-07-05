package com.example.newsapp.repository

import com.example.newsapp.base.BaseRepository
import com.example.newsapp.database.dao.NewsDao
import com.example.newsapp.database.model.News
import com.example.newsapp.network.RetrofitClient
import com.example.newsapp.utils.Const.BASE_URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * The Repository.kt
 * Access point on the DB
 */

class NewsRepository private constructor() : BaseRepository(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var newsDao: NewsDao? = appDatabase.getNotesDao()

    fun getNewsList() = newsDao?.getNews()

    fun saveNews(note: News) {
        launch {
            saveNewsInDB(note)
        }
    }

    private suspend fun saveNewsInDB(note: News) {
        withContext(Dispatchers.IO) {
            newsDao?.setNews(note)
        }
    }

    fun deleteAll() {
        launch {
            withContext(Dispatchers.IO) {
                newsDao?.deleteAll()
            }
        }
    }

    ////API End pints
    fun getNewsArticles(keyword: String, pageNumber: String, apiKey: String) =
        RetrofitClient.getInterfaceService(
            BASE_URL
        ).getNewsArticles(keyword, pageNumber, apiKey)

    companion object {
        private var instance: NewsRepository? = null
        fun getInstance(): NewsRepository {
            if (instance == null)
                instance =
                    NewsRepository()
            return instance!!
        }
    }
}