package com.example.banking.Layout

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banking.Adapter.adapterUser
import com.example.banking.Data.Chat
import com.example.banking.databinding.ActivityScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class Screen : AppCompatActivity() {
    private lateinit var binding: ActivityScreenBinding
    var ds: ArrayList<Chat> = ArrayList()

    var db = Firebase.firestore
    var stoimg = FirebaseStorage.getInstance()
    var auth = FirebaseAuth.getInstance()
    var userID = auth.currentUser!!.uid
    var userAdmin = "K7HUfc95UwUmRDookHFcNPUdf4n2"
   /* var chatDocumentName = if (userID < userAdmin) "$userID-$userAdmin" else "$userAdmin-$userID"*/

    val filename = "${userID}.jpg"
    val storeRef = stoimg.reference.child("Image").child(userID!!).child(filename)

    private var userId: String? = null
    private var userName: String? = null
    var chatdocuser : String? = null
    var chatdocadmin : String? = null
    var chatdocdebux1 : String? = null
    var chatdocdebux2 : String? = null

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var intent = intent
        userId = intent.getStringExtra("user_id")
        userName = intent.getStringExtra("user_name")
         chatdocuser = "$userID-$userAdmin"
         chatdocadmin = "$userAdmin - $userId"
        chatdocdebux1 = "$userAdmin - $userID"
        chatdocdebux2 = "$userId-$userID"
        binding.rcv.adapter = adapterUser(this,ds)
        binding.rcv.layoutManager = LinearLayoutManager(this)


        binding.rcv.adapter?.notifyDataSetChanged()
        binding.imageback.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

     nguoiDung()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun nguoiDung() {
        if (!auth.currentUser!!.uid.equals(userAdmin)) {
            binding.rcv.adapter?.notifyDataSetChanged()
            storeRef.getBytes(1324 * 1324).addOnSuccessListener { byte ->
                val bitmap = BitmapFactory.decodeByteArray(byte, 0, byte.size)
                binding.textName.text = "Hỗ Trợ"


                db.collection("Messages").document(chatdocuser!!).collection("Messages")
                    .orderBy("time")
                    .addSnapshotListener { value, error ->
                        value?.documents?.forEach { document ->
                            val noidung = document.getString("noidung")
                            val time = document.getString("time")
                        /*    var date = document.getString("date")*/
                            addChatToDs(Chat(noidung!!, time!!, bitmap, false))
                        }

                        ds.sortBy { it.time }
                        binding.rcv.adapter?.notifyDataSetChanged()

                        Log.d("sizetn", "nguoiDung: ${ds.size}")
                    }

                db.collection("Messages").document(chatdocdebux1!!).collection("Messages")
                    .orderBy("time")
                    .addSnapshotListener { value, error ->
                        value?.documents?.forEach { document ->
                            val noidung = document.getString("noidung")
                            val time = document.getString("time")
                           /* var date = document.getString("date")*/

                            Log.d("list", "nguoiDung: ok")
                            addChatToDs(Chat(noidung!!, time!!, null, true,))

                        }
                        ds.sortBy { it.time }
                        binding.rcv.adapter?.notifyDataSetChanged()

                    }


                binding.layoutsend.setOnClickListener {
                    /*var times = Instant.now().toEpochMilli()
                    val time = Instant.ofEpochMilli(times).toString()*/
                    val times = Instant.now()
                    val timeid = ZoneId.systemDefault()
                    var timess = DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss").withZone(timeid)
                    var time = timess.format(times)

                    var ndtn = binding.inputmess.text.toString()

                    binding.inputmess.text.clear()

                    Log.d("huhu", "nguoiDung: ${ds.size}")
                    val data = hashMapOf(
                        "noidung" to ndtn,
               /*         "date" to FieldValue.serverTimestamp(),*/
                        "time" to time,
                        "sender" to userID,
                        "receiver" to userAdmin
                    )

                    var docref = db.collection("Messages").document(chatdocuser!!)
                    docref.collection("Messages").add(data).addOnSuccessListener {
                        Log.d("nhantin", "onCreate: Ok")
                    }.addOnFailureListener { e ->
                        Log.d("nhantin", "onCreate: ${e} ")
                    }
                }
            }
        } else {
            binding.textName.text = userName.toString()
            binding.rcv.adapter?.notifyDataSetChanged()
         /*   var userId = intent.getStringExtra("user_id")*/
    /*        val storeReff = stoimg.reference.child("Image").child(userId!!).child(filename)*/
            storeRef.getBytes(1324 * 1324).addOnSuccessListener { byte ->
                val bitmap = BitmapFactory.decodeByteArray(byte, 0, byte.size)
                Log.d("huhu", "nguoiDung: ${bitmap} ")
                db.collection("Messages").document(chatdocadmin!!).collection("Messages")
                    .orderBy("time")
                    .addSnapshotListener { value, error ->
                        value?.documents?.forEach { document ->
                            val noidung = document.getString("noidung")
                            val time = document.getString("time")

                            addChatToDs(Chat(noidung!!, time!!, bitmap, false))
                        }
                        /*50btcWkIy7fmAVh84lNYiR95z7s2-sbB8ryzOZnMHCIsJUcs63mMVjgN2
                    50btcWkIy7fmAVh84lNYiR95z7s2 - sbB8ryzOZnMHCIsJUcs63mMVjgN2*/

                        ds.sortBy { it.time }

                        binding.rcv.adapter?.notifyDataSetChanged()
                    }
                Log.d("chatdoc", "nguoiDung: ${chatdocdebux2}")
                db.collection("Messages").document(chatdocdebux2!!).collection("Messages")
                    .orderBy("time")
                    .addSnapshotListener { value, error ->
                        value?.documents?.forEach { document ->
                            val noidung = document.getString("noidung")
                            val time = document.getString("time")

                            addChatToDs(Chat(noidung!!, time!!, null, true))
                            binding.inputmess.text.clear()
                            ds.sortBy { it.time }
                        }
                        binding.rcv.adapter?.notifyDataSetChanged()
                    }

                binding.layoutsend.setOnClickListener {
                   /* var times = Instant.now().toEpochMilli()
                    val time = Instant.ofEpochMilli(times).toString()*/
                    val times = Instant.now()
                    val timeid = ZoneId.systemDefault()
                    var timess = DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss").withZone(timeid)
                    var time = timess.format(times)

                    var ndtn = binding.inputmess.text.toString()
                    binding.inputmess.text.clear()
                    val data = hashMapOf(
                        "noidung" to ndtn,
                        "time" to time,
                       /* "date" to FieldValue.serverTimestamp(),*/
                        "sender" to userAdmin,
                        "receiver" to userId
                    )

                    var docref = db.collection("Messages").document(chatdocadmin!!)
                    docref.collection("Messages").add(data).addOnSuccessListener {
                        Log.d("nhantin", "onCreate: Ok")


                    }.addOnFailureListener { e ->
                        Log.d("nhantin", "onCreate: ${e} ")
                    }
                }
            }
        }
    }
    private fun addChatToDs(chat: Chat) {
        if (!ds.contains(chat)) {
            ds.add(chat)
        }
    }




}
