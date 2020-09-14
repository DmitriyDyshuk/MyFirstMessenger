package com.example.messengeralpha.ui.main.fragments.chats.presenter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.messengeralpha.R
import com.example.messengeralpha.db.model.User
import com.example.messengeralpha.ui.chat.ChatActivity


class ChatsAdapter(private val context: Context, private val values: ArrayList<User>) : RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {

    override fun getItemCount() = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = values[position]

        holder.tvMessage?.text = "${user.firstName}" + "${user.lastName}"
        if (user.avatarUrl != null) {
            holder.ivPhoto?.let {
                Glide.with(context).load(user.avatarUrl).into(it)
            }
        }

        holder.llUser?.setOnClickListener {
            context.startActivity(Intent(context, ChatActivity::class.java))
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivPhoto = itemView.findViewById(R.id.ivPhoto) as? ImageView
        var tvName = itemView.findViewById(R.id.tvName) as? TextView
        var tvMessage = itemView.findViewById(R.id.tvMessage) as? TextView
        var llUser = itemView.findViewById(R.id.llUser) as? LinearLayout
    }
}