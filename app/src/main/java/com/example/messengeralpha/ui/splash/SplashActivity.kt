package com.example.messengeralpha.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.messengeralpha.ui.login.LoginActivity
import com.example.messengeralpha.R
import com.example.messengeralpha.db.RealmHelper
import com.example.messengeralpha.ui.main.MainActivity
import io.realm.Realm

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Realm.init(this)


        Handler().postDelayed({
            if (RealmHelper().getUser() != null) {
                MainActivity.startActivity(this)
            } else startActivity(Intent(this, LoginActivity::class.java))
            finish()
        },2000)
    }
}