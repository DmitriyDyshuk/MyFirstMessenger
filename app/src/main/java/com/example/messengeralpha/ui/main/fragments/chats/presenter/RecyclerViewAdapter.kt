package com.example.messengeralpha.ui.main.fragments.chats.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messengeralpha.R
import com.example.messengeralpha.db.model.User


class RecyclerViewAdapter(private val values: ArrayList<User>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount() = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = values[position]

        holder.tvMessage?.text = "${user.firstName}" + "${user.lastName}"
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivPhoto = itemView.findViewById(R.id.ivPhoto) as? ImageView
        var tvName = itemView.findViewById(R.id.tvName) as? TextView
        var tvMessage = itemView.findViewById(R.id.tvMessage) as? TextView
    }
}