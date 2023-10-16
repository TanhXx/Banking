package com.example.banking.Adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.banking.Layout.Profile
import com.example.banking.Layout.HomePage

class navigationAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2;
    }

    override fun createFragment(position: Int): Fragment {
        return if(position ==0){
            HomePage()
        }else{
            Profile()
        }
    }
}