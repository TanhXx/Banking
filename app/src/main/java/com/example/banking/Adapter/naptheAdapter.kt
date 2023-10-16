package com.example.banking.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.banking.R

class naptheAdapter(var images : List<Int>) :
    RecyclerView.Adapter<naptheAdapter.naptherViewHolder>() {

    fun updateImages(newImages: List<Int>) {
        images = newImages
        notifyDataSetChanged()
    }


    inner class naptherViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): naptherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_napthe, parent, false)
        return naptherViewHolder(view)
    }

    override fun getItemCount(): Int {
       return images.size
    }

    override fun onBindViewHolder(holder: naptherViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.imageViewnt)
        imageView.setImageResource(images[position])
    }
}
