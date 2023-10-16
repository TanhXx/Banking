package com.example.banking.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.banking.Data.Chat
import com.example.banking.R

class adapterUser(var mContext: Context, var messages: ArrayList<Chat>) :
    RecyclerView.Adapter<adapterUser.ChatviewHolder>() {

    inner class ChatviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ndchat = itemView.findViewById<TextView>(R.id.textmess)
        var time = itemView.findViewById<TextView>(R.id.textdatetime)
        var image = itemView.findViewById<ImageView>(R.id.image_profile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatviewHolder {
        val view: View = when (viewType) {
            VIEW_TYPE_ADMIN -> {
                LayoutInflater.from(parent.context).inflate(R.layout.item_received, parent, false)
            }
            else -> {
                LayoutInflater.from(parent.context).inflate(R.layout.item_sent, parent, false)
            }
        }
        return ChatviewHolder(view)
    }


    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isFromAdmin) {
            VIEW_TYPE_ADMIN
        } else {
            VIEW_TYPE_USER
        }
    }

    override fun onBindViewHolder(holder: ChatviewHolder, position: Int) {
        val current = messages[position]
        holder.ndchat.text = current.noidung
        holder.time.text = current.time

        if (current.image != null) {
            holder.image.setImageBitmap(current.image)
        }
    }
    companion object {
        private const val VIEW_TYPE_ADMIN = 0
        private const val VIEW_TYPE_USER = 1
    }
}

