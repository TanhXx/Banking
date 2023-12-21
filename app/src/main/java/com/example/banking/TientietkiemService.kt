package com.example.banking

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import com.example.banking.Layout.HomePage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TientietkiemService : Service() {
    var handler = Handler()
    var tangtien = object : Runnable {
        override fun run() {
            var db = Firebase.firestore
            var id = HomePage.Userid
            var tientietkiem = HomePage.Tientietkiem
            var tienlaitraiphieu = HomePage.tienlaitraiphieu
            var tienguitp = HomePage.tientraiphieu
            var time = HomePage.timechenhlech

             var tienlai = ((tientietkiem * 0.000109589) * time) + tientietkiem
            var tienlaitp = ((tienguitp * 0.000152055) * HomePage.timechenhlechtp) + tienguitp
            Log.d("tietkiem", "Tiền gốc và lãi của gửi thường hiện tại là: ${tienlai}, bạn đã gửi được ${time} ngày")
            Log.d("tietkiem", "Tiền gốc và lãi của trái phiếu là: ${tienlaitp}, bạn đã gửi được ${HomePage.timechenhlechtp} ngày")

            handler.postDelayed(this,10000)
            var info = hashMapOf(
               "tientang" to tienlai,
                "tienlaitraiphieu" to tienlaitp
            )

            db.collection("Users").document(id)
                .update(info as Map<String,Any>)

        }

    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(tangtien)
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handler.postDelayed(tangtien,2000)
        return START_STICKY

    }




    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}