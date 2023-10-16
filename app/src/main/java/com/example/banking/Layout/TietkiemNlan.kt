package com.example.banking.Layout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.banking.databinding.ActivityTietkiemNlanBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TietkiemNlan : AppCompatActivity() {
    var db = Firebase.firestore
    var sum : Double = 0.0
    private lateinit var binding : ActivityTietkiemNlanBinding
    companion object{
        var tienguiN : Int = 0;
        var timechenhlechN : Int = 0
         var date11 : Date? = null
            var  date22: Date? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTietkiemNlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tietkiemnlan.setOnClickListener {
            hidekeybroadactivity(it)
        }
        var docref = db.collection("TietKiem").document(HomePage.Userid)

        binding.cf.setOnClickListener {
            if(binding.edtst.text == null || binding.edtst.text.toString().toIntOrNull()!! > HomePage.Sodus){
                Toast.makeText(this, "Vui lòng nhập số tiền và check lại số dư", Toast.LENGTH_SHORT).show()
            }
            else{
                var timegui = Date()
                val datefm = SimpleDateFormat("HH:mm - dd/MM/yyyy", Locale.getDefault())
                val time = datefm.format(timegui)


                var tienconlai = HomePage.Sodus - binding.edtst.text.toString().toInt()
                var date2 = datefm.parse(HomePage.timedangnhap)
                var date1 = datefm.parse(time)

                date11 = date1
                date22  = date2

                var timechenhlech = date2.time - date1.time



                var tiengui = binding.edtst.text.toString().toInt()
                var gocvalai = tiengui + ((tiengui * 0.0082)) * timechenhlech

                tienguiN = tiengui
                timechenhlechN = timechenhlech.toInt()

                val info = hashMapOf(
                    "tiengui" to binding.edtst.text.toString(),
                    "ngaygui" to time,
                    "timechenhlech" to timechenhlech,
                    "timedangnhap" to HomePage.timedangnhap,
                    "Gocvalai" to gocvalai
                )
                val updatesd = hashMapOf(
                    "sodu" to tienconlai
                )

                db.collection("Users").document(HomePage.Userid)
                    .update(updatesd as Map<String,Any>)


                docref.collection("TietKiem").document(System.currentTimeMillis().toString())
                    .set(info).addOnSuccessListener {
                        Toast.makeText(this, "Gửi thành công", Toast.LENGTH_SHORT).show()
                        var intent = Intent(this, Success::class.java)
                        startActivity(intent)
                    }
                var Gocvalai = 0.0

                db.collection("TietKiem").document(HomePage.Userid).collection("TietKiem")
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            val gocvalai = document.getDouble("Gocvalai")
                            if (gocvalai != null) {
                                Gocvalai += gocvalai
                            }
                        }
                        Log.d("Total Gocvalai", "Total: $Gocvalai")
                    }





            }
        }

    }

    fun hidekeybroadactivity(view : View){
        if(view!= null){
            val iim = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            iim.hideSoftInputFromWindow(view.windowToken,0)
        }
    }
}