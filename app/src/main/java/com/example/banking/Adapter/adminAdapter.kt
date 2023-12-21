package com.example.banking.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.banking.Data.dataAdmin

class   adminAdapter(var mContext : Context, var ds: ArrayList<dataAdmin>) :
    RecyclerView.Adapter<adminAdapter.adminviewHolder>() {
    inner class adminviewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var ndchat = itemView.findViewById<TextView>(com.example.banking.R.id.textmess)
        var time = itemView.findViewById<TextView>(com.example.banking.R.id.textdatetime)
        var image = itemView.findViewById<ImageView>(com.example.banking.R.id.image_profile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adminviewHolder {
        var view = LayoutInflater.from(parent.context).inflate(com.example.banking.R.layout.item_received,parent,false)
        return adminviewHolder(view)
    }

    override fun getItemCount(): Int {
        return ds.size
    }

    override fun onBindViewHolder(holder: adminviewHolder, position: Int) {
        var currentItem = ds[position]
        holder.time.text = currentItem.time
        holder.ndchat.text = currentItem.noidung
    }
}