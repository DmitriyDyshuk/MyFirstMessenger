package com.example.messengeralpha.db

enum class References {
    USERS {
        override fun toString() = "users"
    },
    IMAGES {
        override fun toString() = "images"
    }
}