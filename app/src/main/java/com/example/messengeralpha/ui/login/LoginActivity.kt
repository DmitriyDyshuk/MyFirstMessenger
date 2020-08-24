package com.example.messengeralpha.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.messengeralpha.R
import com.example.messengeralpha.db.FirebaseDatabase
import com.example.messengeralpha.db.callbacks.OnLoginListener
import com.example.messengeralpha.db.model.User
import com.example.messengeralpha.ui.main.MainActivity
import com.example.messengeralpha.ui.registration.RegistrationActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tvRegistration.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        btnLogin.setOnClickListener {
            FirebaseDatabase().checkOnUserExist(etEmail.text.toString(), etPassword.text.toString(), object :
                OnLoginListener {
                override fun onUserWasReceived(user: User) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onUserDoseNotExist() {
                    Toast.makeText(this@LoginActivity, "Пользователя с таким email не существует", Toast.LENGTH_LONG).show()
                }

                override fun onPasswordError() {
                    Toast.makeText(this@LoginActivity, "Неверный пароль", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}