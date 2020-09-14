package com.example.messengeralpha.utils

import android.util.Patterns
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File

fun String.isEmail() : Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun ImageView.loadFile(file: File?, newWidth: Int? = null, newHeight: Int? = null) {
    var loader = Glide.with(this.context)
        .load(file)

    if (newWidth != null && newHeight != null) {
        loader = loader.override(newWidth, newHeight)
    }

    loader.into(this)
}