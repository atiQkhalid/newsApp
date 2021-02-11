package com.example.newsapp.network

import com.example.newsapp.database.model.response.NewsDao
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * The APIInterface.kt
 */
interface ApiInterface {

    @GET("everything")
    fun getNewsArticles(
        @Query("q") keyword: String, @Query("page") page: String,
        @Query("apiKey") apiKey: String,
    ): Call<NewsDao>
}
