package com.example.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.database.dao.NewsDao
import com.example.newsapp.database.model.News
/**
 * The AppDatabase.kt
 * Main DB for the app
 */
@Database(entities = [News::class], version =2 , exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NewsDao

    companion object {

        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, "message_database"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}