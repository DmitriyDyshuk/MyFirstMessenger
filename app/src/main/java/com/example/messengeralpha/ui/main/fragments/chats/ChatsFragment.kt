package com.example.messengeralpha.ui.main.fragments.chats


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messengeralpha.R
import com.example.messengeralpha.db.FirebaseDatabase
import com.example.messengeralpha.db.model.User
import com.example.messengeralpha.db.callbacks.OnChatsListener
import com.example.messengeralpha.ui.main.fragments.chats.presenter.RecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_chats.*


class ChatsFragment : Fragment() {

    private val itemList = ArrayList<User>()
    private val adapter = RecyclerViewAdapter(itemList)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        reloadUsers()
    }

    private fun initAdapter() {
        rvMyChats.layoutManager = LinearLayoutManager(view?.context)
        rvMyChats.adapter = adapter
    }

    private  fun reloadUsers() {
        FirebaseDatabase().getUsers( object : OnChatsListener {
            override fun onUserWasReceived(users: ArrayList<User>) {
                itemList.clear()
                itemList.addAll(users)
                adapter.notifyDataSetChanged()
            }
        })
    }

}