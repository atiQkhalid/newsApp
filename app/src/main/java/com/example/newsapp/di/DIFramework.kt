package com.example.newsapp.di

import android.content.Context
import com.example.newsapp.database.AppDatabase
import com.example.newsapp.repository.NewsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * DIFramework.kt
 * The dependency injection framework used by the app.
 * uses Koin for DI.
 */
object DIFramework {

    fun init(context: Context) {
        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(context)
            val repoModule = module {
                single { AppDatabase.getDatabase(androidContext()) }
                single { NewsRepository.getInstance() }
            }
            // declare modules
            modules(repoModule)
        }
    }
}