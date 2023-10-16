package com.example.banking.Layout

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.banking.Adapter.navigationAdapter
import com.example.banking.R
import com.example.banking.databinding.FragmentMainLayoutBinding


class Main : Fragment() {

    lateinit var binding : FragmentMainLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var viewPager = view?.findViewById<ViewPager2>(R.id.viewpager2)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        // Inflate the layout for this fragment
        binding = FragmentMainLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.viewpager2.adapter = navigationAdapter(this)
        binding.viewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.navi.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.fhome -> binding.viewpager2.currentItem = 0
                R.id.fprofile -> binding.viewpager2.currentItem = 1
            }
            true
        }


        binding.viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
               when(position){
                   0-> binding.navi.selectedItemId = R.id.fhome
                   1-> binding.navi.selectedItemId = R.id.fprofile
               }
            }
        })



    }


}