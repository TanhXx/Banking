package com.example.banking.Layout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.banking.databinding.ActivityChungChiBaoLocBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ChungChiBaoLoc : AppCompatActivity() {
    private lateinit var binding: ActivityChungChiBaoLocBinding
    var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChungChiBaoLocBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ccbl.setOnClickListener {
            hidekeybroadactivity(it)
        }
        var id = HomePage.Userid
        var tongtien = HomePage.TongCks
        var sodu  = HomePage.Sodus
        var tientietkiem = HomePage.Tientietkiem
        var timechenhlech = HomePage.timechenhlech

        var img = binding.demo
        Glide.with(this).load(com.example.banking.R.drawable.bonptgif).into(img)

        Log.d("huhu", "Tong tien la: ${tongtien} + ${sodu} + ${id}  + ${tientietkiem}")

        var fm = sodu.toInt()
        var fms = formatDoubleWithCommas(fm)


        /*binding.soduchovay.text = fms.toString() + "$"*/

        /*var intentService = Intent(this, TientietkiemService::class.java)
        startService(intentService)
*/

       binding.cf.setOnClickListener {
           val tiennhapText = binding.edtst.text.toString()

           if (tiennhapText.isEmpty()) {
               Toast.makeText(this, "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show()
           } else {
               val tiennhap = tiennhapText.toDoubleOrNull()

               if (tiennhap != null) {
                   if (tiennhap > sodu) {
                       Toast.makeText(this, "Tài khoản không đủ để thực hiện giao dịch", Toast.LENGTH_SHORT).show()
                   } else if (tiennhap % 10 != 0.0) {
                       Toast.makeText(this, "Tiền gửi phải là bội của 10", Toast.LENGTH_SHORT).show()
                   } else if (timechenhlech > 0) {
                       Toast.makeText(this, "Mỗi cá nhân chỉ được gửi tiết kiệm 1 lần", Toast.LENGTH_SHORT).show()
                   } else {
                       sodu -= tiennhap
                       tientietkiem += tiennhap

                       val timegui = Date()
                       val datefm = SimpleDateFormat("HH:mm - dd/MM/yyyy", Locale.getDefault())
                       val time = datefm.format(timegui)

                       val info = hashMapOf(
                           "sodu" to sodu,


                           "Tientietkiem" to tientietkiem,
                           "timebatdaugui" to time
                       )

                       db.collection("Users").document(id)
                           .update(info as Map<String, Any>)
                       var intent = Intent(this,Success::class.java)
                       startActivity(intent)
                   }
               } else {
                   Toast.makeText(this, "Số tiền không hợp lệ", Toast.LENGTH_SHORT).show()
               }
           }




       }

        /*binding.demo.setOnClickListener {
            var intentService = Intent(this, TientietkiemService::class.java)
            stopService(intentService)
        }*/


    }

    fun hidekeybroadactivity(view : View){
        if(view!= null){
            val iim = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            iim.hideSoftInputFromWindow(view.windowToken,0)
        }
    }

    fun formatDoubleWithCommas(number: Int): String {
        return String.format("%,d", number)
    }


}