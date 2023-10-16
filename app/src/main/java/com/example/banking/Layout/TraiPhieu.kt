package com.example.banking.Layout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.banking.databinding.ActivityTraiPhieuBinding


class TraiPhieu : AppCompatActivity() {
    private lateinit var binding : ActivityTraiPhieuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTraiPhieuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnmua.setOnClickListener {
            var bottomSheetTraiPhieu = BottomSheetTraiPhieu()
            bottomSheetTraiPhieu.show(supportFragmentManager,bottomSheetTraiPhieu.tag)
        }


    }
}