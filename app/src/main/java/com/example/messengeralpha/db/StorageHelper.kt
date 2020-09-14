package com.example.messengeralpha.db

import android.net.Uri
import com.example.messengeralpha.db.callbacks.OnPhotoUploadListener
import com.example.messengeralpha.db.model.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class StorageHelper {

    val storage = Firebase.storage
    val storageRef = storage.reference

    fun uploadUserPhoto(user: User, uri: Uri, listener: OnPhotoUploadListener) {
        val avatarRef = storageRef.child("${References.IMAGES}/${user.id}/avatar.jpg")
        val uploadTask = avatarRef.putFile(uri)

        uploadTask.addOnSuccessListener {

            avatarRef.downloadUrl.addOnSuccessListener {
                listener.onSuccess(it.toString())
            }.addOnFailureListener {
                it.printStackTrace()
                listener.onFailure()
            }
        }.addOnFailureListener {
            it.printStackTrace()
            listener.onFailure()
        }
    }

}