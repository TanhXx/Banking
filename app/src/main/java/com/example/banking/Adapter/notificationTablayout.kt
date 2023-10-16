package com.example.banking.Adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.banking.Layout.Biendongsodu
import com.example.banking.Layout.Notification
import com.example.banking.Layout.Thongbao

class notificationTablayout(fragment: Notification) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if(position == 0){
            Thongbao()
        }else {
            Biendongsodu()
        }
    }
}