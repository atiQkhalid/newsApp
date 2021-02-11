package com.example.newsapp.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * A convenient function to load images from internet on ImageView.
 * @see Glide The library used which loads memory cached images.
 */
fun ImageView.load(url: String?) {
    Glide.with(this).load(url).into(this)
}