package com.example.banking.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.banking.Data.lichsuGd
import com.example.banking.Layout.Thongbao
import com.example.banking.R

class lichsuAdapter(var mconText: Thongbao, var ds:  ArrayList<lichsuGd>) :
    RecyclerView.Adapter<lichsuAdapter.lsViewholder>() {
    inner class lsViewholder(itemview : View) : RecyclerView.ViewHolder(itemview)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): lsViewholder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_noti,parent,false)
        return lsViewholder(view)
    }

    override fun getItemCount(): Int {
        return ds.size
    }

    override fun onBindViewHolder(holder: lsViewholder, position: Int) {
        val currentItem = ds[position]

        var ls = holder.itemView.findViewById<TextView>(R.id.item123)

        ls.text = currentItem.dong

    }
}