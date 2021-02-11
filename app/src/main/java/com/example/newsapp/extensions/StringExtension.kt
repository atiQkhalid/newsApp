package com.example.newsapp.extensions

import android.annotation.SuppressLint
import android.util.Patterns
import java.text.SimpleDateFormat
import java.util.*

/**
 * Extension function to convert timestamp to date
 */
@SuppressLint("SimpleDateFormat")
fun Long.convertDateToFormat(): String{
    return SimpleDateFormat("MM/dd/yyyy").format( Date(this))
}

/**
 * Extension function to Verify the URL
 */
fun String.checkValidURL(): String?{
    if(Patterns.WEB_URL.matcher(this).matches())
        return this

    return null
}