package com.example.banking.Adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banking.Data.Parentitem

class ParentAdapter(val parentList : List<Parentitem>):
    RecyclerView.Adapter<ParentAdapter.ParentViewholder>() {
    inner class ParentViewholder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewholder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return parentList.size
    }

    override fun onBindViewHolder(holder: ParentViewholder, position: Int) {
        TODO("Not yet implemented")
    }


}