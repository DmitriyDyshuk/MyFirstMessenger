package com.example.messengeralpha.ui.main

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.messengeralpha.R
import com.example.messengeralpha.ui.main.presenter.MainPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        fun startActivity(context: Context?) {
            context?.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdapter()

        llMyChats.setOnClickListener {
            vpPager.currentItem = 0
            setActiveTab(ivMyChats, tvMyChats)
            setDisableTab(ivSettings, tvSettings)
        }

        llSettings.setOnClickListener {
            vpPager.currentItem = 1
            setActiveTab(ivSettings, tvSettings)
            setDisableTab(ivMyChats, tvMyChats)
        }
    }

    private fun initAdapter() {
        val adapter = MainPagerAdapter(supportFragmentManager)
        vpPager.adapter = adapter
    }

    private fun setActiveTab(icon: ImageView, title: TextView) {
        title.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            icon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorWhite))
        }
    }

    private fun setDisableTab(icon: ImageView, title: TextView) {
        title.setTextColor(ContextCompat.getColor(this, R.color.colorDisableText))
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            icon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorDisableText))
        }
    }
}