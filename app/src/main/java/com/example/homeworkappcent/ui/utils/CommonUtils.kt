package com.example.homeworkappcent.ui.utils

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

object CommonUtils {

    fun loadImageByGlide(url: String, imageView: ImageView, activity: AppCompatActivity) {
        Glide.with(activity)
            .load(url)
            .centerCrop()
            .into(imageView)
    }
}