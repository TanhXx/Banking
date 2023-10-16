package com.example.banking.Layout

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.banking.R
import com.example.banking.databinding.ActivityInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private lateinit var binding : ActivityInfoBinding
var auth = FirebaseAuth.getInstance()
var db = Firebase.firestore
class Info : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        var userUID = auth.currentUser!!.uid

        db.collection("Users").document(userUID).addSnapshotListener { value, error ->
            if(value != null && value.exists()) {
                val names = value.getString("name")
                val sdts = value.getString("SDT")
               val gmails = value.getString("gmail")
                val age = value.getString("age")

                binding.name.text = names
                binding.sdt.text = sdts
                binding.mail.text = gmails
                binding.tuoi.text = age

            }
            else{
                Toast.makeText(this, "dữ liệu k tồn tại", Toast.LENGTH_SHORT).show()
            }
        }
    }
}