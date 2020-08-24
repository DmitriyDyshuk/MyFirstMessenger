package com.example.messengeralpha.ui.main.presenter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.messengeralpha.ui.main.fragments.chats.ChatsFragment
import com.example.messengeralpha.ui.main.fragments.settings.SettingsFragment

class MainPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragments = arrayListOf(
        ChatsFragment(),
        SettingsFragment()
    )

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Tab " + (position + 1)
    }

}