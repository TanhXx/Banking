package com.example.banking.Layout

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.banking.R
import com.example.banking.databinding.ActivitySplashTuVanBinding

private lateinit var binding: ActivitySplashTuVanBinding
class SplashTuVan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashTuVanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*object : CountDownTimer(1000,20000){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                var intent = Intent(this@SplashTuVan,Tuvanvaymuanha::class.java)
                startActivity(intent)
                finish()

            }

        }.start()*/

        Glide.with(this).load(R.drawable.warning).into(binding.img)

        binding.back.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }


    }
}