package com.example.banking.Layout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.banking.databinding.ActivityTietkiemBinding

private lateinit var binding : ActivityTietkiemBinding
class Tietkiem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTietkiemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.cardview1.setOnClickListener {
            var intent = Intent(this,ChungChiBaoLoc::class.java)
            startActivityForResult(intent,1)
        }

        binding.cardview2.setOnClickListener {
            var intent = Intent(this, TraiPhieu::class.java)
            startActivityForResult(intent,1)
        }

        binding.cardview3.setOnClickListener {
            var intent = Intent(this, TietkiemNlan::class.java)
            startActivity(intent)
        }
    }
}