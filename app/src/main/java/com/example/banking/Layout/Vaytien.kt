package com.example.banking.Layout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.banking.databinding.ActivityVaytienBinding

private lateinit var binding : ActivityVaytienBinding
class Vaytien : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVaytienBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vaymuanha.setOnClickListener {
                var intent = Intent(this,Tuvanvaymuanha::class.java)
            startActivity(intent)
        }
    }
}