package com.example.banking.Layout

import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.banking.R

class Success : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        val imageView = findViewById<ImageView>(R.id.imageView2)
        val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        imageView.startAnimation(rotateAnimation)

        object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                supportFragmentManager.beginTransaction().replace(R.id.holderss, Main()).commit()
            }
        }.start()


    }
}