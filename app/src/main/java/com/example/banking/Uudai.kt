package com.example.banking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.banking.databinding.ActivityUudaiBinding

private lateinit var binding : ActivityUudaiBinding
class Uudai : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uudai)
        binding = ActivityUudaiBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

