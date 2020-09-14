package com.example.messengeralpha.ui.registration

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.messengeralpha.R
import com.example.messengeralpha.db.FirebaseDatabase
import com.example.messengeralpha.db.RealmHelper
import com.example.messengeralpha.db.StorageHelper
import com.example.messengeralpha.db.callbacks.OnLoginListener
import com.example.messengeralpha.db.callbacks.OnPhotoUploadListener
import com.example.messengeralpha.db.model.User
import com.example.messengeralpha.ui.main.MainActivity
import com.example.messengeralpha.utils.isEmail
import kotlinx.android.synthetic.main.activity_registration.*


class RegistrationActivity : AppCompatActivity() {

    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }

    var uriAvatar: Uri? = null
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
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Пользователь с таким email уже зарегистрирован",
                        Toast.LENGTH_LONG
                    ).show()
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
                            RealmHelper().saveUserToRealm(user)
                            fb?.addUser(user)
                            fb = null

                            if (uriAvatar != null) {
                                uriAvatar?.let {
                                    StorageHelper().uploadUserPhoto(user, it, object : OnPhotoUploadListener {
                                        override fun onSuccess(url: String) {
                                            user.avatarUrl = url
                                            FirebaseDatabase().addUser(user)
                                            RealmHelper().saveUserToRealm(user)
                                            MainActivity.startActivity(this@RegistrationActivity)
                                        }

                                        override fun onFailure() {
                                            MainActivity.startActivity(this@RegistrationActivity)
                                        }

                                    })
                                }

                            } else {
                                MainActivity.startActivity(this@RegistrationActivity)
                            }

                        }
                    }
                }

                override fun onPasswordError() {}

            })
        }

        ivAddPhoto.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else {
                    pickImageFromGallery()
                }
            }
            else {
                pickImageFromGallery()
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                }
                else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            ivAddPhoto.setImageURI(data?.data)
            uriAvatar = data?.data
            Log.e("dataPrint", "data: ${data?.data}")
        }
    }

}
