package com.example.messengeralpha.db.callbacks

import com.example.messengeralpha.db.model.User


interface OnChatsListener {
    fun onUserWasReceived(users: ArrayList<User>)
}