package com.example.messengeralpha.ui.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.messengeralpha.R
import com.example.messengeralpha.db.FirebaseDatabase
import com.example.messengeralpha.db.RealmHelper
import com.example.messengeralpha.db.model.User
import com.example.messengeralpha.db.callbacks.OnLoginListener
import com.example.messengeralpha.ui.main.MainActivity
import com.example.messengeralpha.utils.isEmail
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    var fb: FirebaseDatabase? = FirebaseDatabase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        ivBack.setOnClickListener {
            finish()
        }

        btnRegistration.setOnClickListener {
            fb?.checkOnUserExist(etEmail.text.toString(), etPassword.text.toString(), object :
                OnLoginListener {
                override fun onUserWasReceived(user: User) {
                    Toast.makeText(this@RegistrationActivity, "Пользователь с таким email уже зарегистрирован", Toast.LENGTH_LONG).show()
                }

                override fun onUserDoseNotExist() {
                    when {
                        etFirstName.text.toString().isEmpty() -> Toast.makeText(this@RegistrationActivity, "Поле Имя не должно быть пустым", Toast.LENGTH_LONG).show()
                        etLastName.text.toString().isEmpty() -> Toast.makeText(this@RegistrationActivity, "Поле Фамилия не должно быть пустым", Toast.LENGTH_LONG).show()
                        !etEmail.text.toString().isEmail() -> Toast.makeText(this@RegistrationActivity, "Введите правильный email", Toast.LENGTH_LONG).show()
                        etPassword.text.toString().replace(" ", "").length < 6 -> Toast.makeText(this@RegistrationActivity, "Пароль должекн быть больше 6 символов", Toast.LENGTH_LONG).show()
                        etRepeatPassword.text.toString() != etPassword.text.toString() -> Toast.makeText(this@RegistrationActivity, "Пароли не совпадают", Toast.LENGTH_LONG).show()
                        else -> {
                            val user = User()
                            user.firstName = etFirstName.text.toString()
                            user.lastName = etLastName.text.toString()
                            user.email = etEmail.text.toString()
                            user.password = etPassword.text.toString().replace(" ", "")
                            fb?.addUser(user)
                            fb = null
                            RealmHelper().saveUserToRealm(user)
                            MainActivity.startActivity(this@RegistrationActivity)
                        }
                    }
                }

                override fun onPasswordError() {}

            })

        }
    }
}