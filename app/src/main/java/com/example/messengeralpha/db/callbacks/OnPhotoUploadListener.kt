package com.example.messengeralpha.db.callbacks

interface OnPhotoUploadListener {
    fun onSuccess(url: String)
    fun onFailure()
}