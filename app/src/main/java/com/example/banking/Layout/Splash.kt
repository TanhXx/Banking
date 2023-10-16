package com.example.banking.Layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.banking.R
import com.google.firebase.messaging.FirebaseMessaging

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        object : CountDownTimer(1000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                supportFragmentManager.beginTransaction().replace(R.id.holder, Login()).commit()
            }
        }.start()

        FirebaseMessaging.getInstance().subscribeToTopic("notification")

    }
}