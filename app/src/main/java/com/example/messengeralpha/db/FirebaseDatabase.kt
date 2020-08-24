package com.example.messengeralpha.db

import android.util.Log
import com.example.messengeralpha.db.callbacks.OnChatsListener
import com.example.messengeralpha.db.callbacks.OnLoginListener
import com.example.messengeralpha.db.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class FirebaseDatabase {

    private val db = Firebase.database

    fun addUser(user: User) {
        val reference = db.getReference(References.USERS.toString())
        val id = reference.push().key
        user.id = id
        reference.child(id.toString()).setValue(user)
        Log.e("TagID", "$id")
    }

    fun checkOnUserExist(email: String, password: String, listener: OnLoginListener) {
        db.getReference(References.USERS.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val users = ArrayList<User>()
                snapshot.children.forEach {
                    val user = it.getValue(User::class.java)
                    if (user != null) {
                        users.add(user)
                    }
                }

                if (users.any { it.email == email }) {
                    val user = users.firstOrNull { it.email == email }
                    if (user?.password == password) {
                        listener.onUserWasReceived(user)
                    } else {
                        listener.onPasswordError()
                    }
                } else {
                    listener.onUserDoseNotExist()
                }
            }
        })
    }

    fun getUsers(listener: OnChatsListener) {
        db.getReference(References.USERS.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val users = ArrayList<User>()
                snapshot.children.forEach {
                    val user = it.getValue(User::class.java)
                    val realmUser = RealmHelper().getUser()
                    if (user != null && user.id != realmUser?.id) {
                        users.add(user)
                    }
                }
                listener.onUserWasReceived(users)
            }
        })
    }

}