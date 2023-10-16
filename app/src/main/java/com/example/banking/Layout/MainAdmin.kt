package com.example.banking.Layout

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banking.Adapter.userAdapter
import com.example.banking.Data.User
import com.example.banking.databinding.ActivityMainAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

private lateinit var binding: ActivityMainAdminBinding
private var ds: ArrayList<User> = ArrayList()
var bitmap: Bitmap? = null

class MainAdmin : AppCompatActivity() {
    var stoimg = FirebaseStorage.getInstance()
    var auth = FirebaseAuth.getInstance()
    var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val userAdapter = userAdapter(this, ds)
        binding.rcv.adapter = userAdapter
        binding.rcv.layoutManager = LinearLayoutManager(this)



        userAdapter.setOnItemClickListener(object : userAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val clickedUser = ds[position]
                val intent = Intent(this@MainAdmin, Screen::class.java)

                intent.putExtra("user_id", clickedUser.id)
                intent.putExtra("user_name", clickedUser.name)


                startActivity(intent)
            }
        })


        getUser()


        val userID = auth.currentUser?.uid
        db.collection("Users").document(userID.toString()).addSnapshotListener { value, error ->
            if (value != null && value.exists()) {
                val names = value.getString("name")
                binding.nameadmin.text = names!!
            }
        }
    }

    fun getUser() {
        db.collection("Users").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                ds.clear()
                for (document in task.result!!) {
                    val name = document.getString("name")
                    val gmail = document.getString("gmail")
                    val id = document.getString("id")
                    val filename = "${id}.jpg"
                    val storeRef = stoimg.reference.child("Image").child(id!!).child(filename)

                    storeRef.getBytes(1324 * 1324).addOnSuccessListener { byte ->
                        val bitmap = BitmapFactory.decodeByteArray(byte, 0, byte.size)
                        Log.d("TanhX", "${id} + ${bitmap}")

                        val user = User(name, gmail, bitmap,id)
                        if (!name.equals("Support")) {
                            ds.add(user)

                        }
                        binding.rcv.adapter?.notifyDataSetChanged()
                    }.addOnFailureListener { e ->
                        Log.d("TanhX", "getUser: lỗi ${e}")
                    }
                }
            }
        }
    }
}
