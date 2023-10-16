package com.example.banking.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.banking.Data.User
import com.example.banking.R
import com.squareup.picasso.Picasso
import java.io.FileOutputStream
private var listener: AdapterView.OnItemClickListener? = null

class userAdapter(var mContext: Context, var ds : ArrayList<User>) :
    RecyclerView.Adapter<userAdapter.userViewHolder>() {


    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var listener: OnItemClickListener? = null


    inner class userViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var names = itemView.findViewById<TextView>(R.id.textname)
        var gmails = itemView.findViewById<TextView>(R.id.textemail)
        var images = itemView.findViewById<ImageView>(R.id.imageViewadmin)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_container_user, parent, false)
        return userViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ds.size
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
        var currenItem = ds[position]
        holder.names.text = currenItem.name
        holder.gmails.text = currenItem.gmail

        val tempImagePath = "${holder.itemView.context.cacheDir.absolutePath}/${currenItem.name}_image.jpg"
        val fileOutputStream = FileOutputStream(tempImagePath)
        currenItem.image?.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()
        if (currenItem.image != null) {
            Picasso.get().load("file://$tempImagePath").into(holder.images)
        }
        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
    }

        fun setOnItemClickListener(clickListener: OnItemClickListener) {
            listener = clickListener
        }
    }

