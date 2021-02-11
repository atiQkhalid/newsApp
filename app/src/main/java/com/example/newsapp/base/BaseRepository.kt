package com.example.newsapp.base

import com.example.newsapp.database.AppDatabase
import org.koin.java.KoinJavaComponent.inject

/**
 * The BaseRepository.kt
 */
abstract class BaseRepository{
    //injecting the DB object to the repository
    protected val appDatabase: AppDatabase by inject(AppDatabase::class.java)
}
