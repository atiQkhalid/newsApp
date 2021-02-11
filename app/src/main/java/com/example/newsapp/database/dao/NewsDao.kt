package com.example.newsapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.database.model.News

/**
 * The NotesDao.kt
 * To run all the db transactions
 */

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setNews(note: News) : Long

    @Query("SELECT * from news_table ORDER BY id ASC")
    fun getNews(): LiveData<List<News>>

    @Query("DELETE FROM news_table")
    fun deleteAll()

    @Query("SELECT * FROM news_table WHERE id=:id ")
    suspend fun getSingleNews(id: Int): News?

    @Update
    fun updateNote(vararg note: News) : Int

    @Query("DELETE FROM news_table WHERE id = :id")
    fun deleteById(id: Int)
}