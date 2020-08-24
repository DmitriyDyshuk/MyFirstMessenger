package com.example.messengeralpha.db

import com.example.messengeralpha.db.model.User
import io.realm.Realm
import io.realm.RealmConfiguration


class RealmHelper() {

    private val realm = Realm.getInstance(
        RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
    )

    fun saveUserToRealm(user: User) {
        realm.executeTransaction { bgRealm ->
            bgRealm.where(User::class.java).findAll().deleteAllFromRealm()
            bgRealm.copyToRealmOrUpdate(user)
        }
    }

    fun getUser(): User? {
        val user = realm.where(User::class.java).findFirst()
        return if (user != null) {
            realm.copyFromRealm(user)
        } else null
    }

}