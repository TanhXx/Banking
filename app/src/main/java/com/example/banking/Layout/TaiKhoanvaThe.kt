package com.example.banking.Layout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.banking.databinding.ActivityTaiKhoanvaTheBinding

class TaiKhoanvaThe : AppCompatActivity() {
    private lateinit var binding : ActivityTaiKhoanvaTheBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaiKhoanvaTheBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}