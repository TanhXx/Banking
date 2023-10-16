package com.example.banking.Layout

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AppBroadcast : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Toast.makeText(p0, p1?.action, Toast.LENGTH_SHORT).show()
        when(p1?.action){
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> Toast.makeText(p0, "huhu", Toast.LENGTH_SHORT).show()
        }


    }
}