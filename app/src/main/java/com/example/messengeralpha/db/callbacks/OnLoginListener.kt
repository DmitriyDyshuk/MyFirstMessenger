package com.example.messengeralpha.db.callbacks

import com.example.messengeralpha.db.model.User

interface OnLoginListener {
    fun onUserWasReceived(user: User)
    fun onUserDoseNotExist()
    fun onPasswordError()
}