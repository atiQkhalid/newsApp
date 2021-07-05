package com.example.newsapp.activities

import android.os.Bundle
import com.example.newsapp.R
import com.example.newsapp.base.BaseActivity
import com.example.newsapp.extensions.replaceFragmentSafely
import com.example.newsapp.fragments.HomeFragment

/**
 * MainActivity.kt, Main activity class, launcher activity
 */
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragmentSafely(HomeFragment())
    }
}