package com.example.banking.Layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.banking.Adapter.notificationTablayout
import com.example.banking.R
import com.example.banking.databinding.ActivityNotificationBinding
import com.google.android.material.tabs.TabLayoutMediator

class Notification : AppCompatActivity() {
    lateinit var binding : ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewpager2.adapter = notificationTablayout(this)

        tablayout()
    }


    private fun tablayout(){
        TabLayoutMediator(binding.tablayout, binding.viewpager2){tab, posiiton ->
            when(posiiton){
                0 -> tab.text = "Biến động"
                1-> tab.text = "Thông Báo"
            }

        }.attach()
    }
}