package com.example.banking.Layout

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.banking.TientietkiemService
import com.example.banking.databinding.ActivityTuvanvaymuanhaBinding

private lateinit var binding : ActivityTuvanvaymuanhaBinding
class Tuvanvaymuanha : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityTuvanvaymuanhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tuvan2.setOnClickListener {
            var intent = Intent(this,TientietkiemService::class.java)
            stopService(intent)
        }
        var tongck = HomePage.TongCks

        binding.tuvanmienphi.setOnClickListener {
           if(tongck > 1000){

                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:0383865954")
                startActivity(intent)
            }else{
            Toast.makeText(this, "Phải phải chuyển khoảng trên 1000$", Toast.LENGTH_SHORT).show()
                var intent = Intent(this@Tuvanvaymuanha,SplashTuVan::class.java)
                startActivityForResult(intent,1)

            }


        }


    }
}