package com.example.banking.Layout

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.banking.Adapter.naptheAdapter
import com.example.banking.R
import com.example.banking.databinding.ActivityNaptheBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Napthe : AppCompatActivity() {
    private var currentPage = 0
    private val images = listOf(R.drawable.tt3,R.drawable.tt2, R.drawable.tt)
    private lateinit var binding: ActivityNaptheBinding
    var db = Firebase.firestore
    var auth = FirebaseAuth.getInstance()
    var stn : Double = 0.0
    var UserID = auth.currentUser!!.uid
    var sdt : String = ""
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaptheBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.layoutnt.setOnClickListener {
            hidekeybroadactivity(it)
        }
         var adapter = naptheAdapter(images)
        binding.viewpager2.adapter = adapter

        val handler = Handler()

        val updateRunnable = object : Runnable {
            override fun run() {
                if (currentPage == images.size) {
                    currentPage = 0
                }
                binding.viewpager2.setCurrentItem(currentPage, true)
                currentPage++
                handler.postDelayed(this, 3000)
            }
        }

        handler.postDelayed(updateRunnable, 3000)

        binding.danhba.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            startActivityForResult(intent, 1)
        }
        changecolor()

        binding.back.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.napngay.setOnClickListener {
            val currentDateTime = LocalDateTime.now()
            val format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            val formatDatetime = currentDateTime.format(format)

            var sdtnap = binding.sdt.text.toString()
            sdtnap =sdtnap.replace("\\s+".toRegex() ,"")

            if (!sdtnap.isNullOrEmpty() && stn>0){
                db.collection("Users").document(UserID).get().addOnSuccessListener { document ->
                    if(document.exists()){
                        var sotiens = document.getDouble("sodu")
                        if (sotiens!! > stn!!){
                            var sotiencuoi = (sotiens - stn) +  (stn*0.1)
                            var info = hashMapOf(
                                "sodu" to sotiencuoi
                            )
                            db.collection("Users").document(UserID).update(info as Map<String, Any>)
                            val docrefsd= db.collection("Users").document(UserID)
                            val newBankingData = "Nạp ${stn}$ cho số điện thoại: ${sdtnap},lúc ${formatDatetime} số dư cuối giao dịch: ${sotiencuoi}$"
                            docrefsd.update("banking", FieldValue.arrayUnion(newBankingData))
                            var intent = Intent(this@Napthe,Success::class.java)
                            startActivity(intent)

                        }else   {
                            Toast.makeText(this, "Tài khoản không đủ để thực hiện giao dịch", Toast.LENGTH_SHORT).show()
                        }
                    }

                }

            }
            else{
                Toast.makeText(this, "Vui lòng nhập số điện thoại và chọn số tiền", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun hidekeybroadactivity(view : View){
        if(view!= null){
            val iim = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            iim.hideSoftInputFromWindow(view.windowToken,0)
        }
    }
    

    private fun changecolor() {
        val linearLayoutList = listOf(
            R.id.muoi_linearlayout, R.id.haimuoi_linearlayout, R.id.nammuoi_linearlayout,
            R.id.mottram_linearlayout, R.id.haitram_linearlayout, R.id.namtram_linearlayout
        )

        val textViewList = listOf(
            binding.muoi, binding.haimuoi, binding.nammuoi,
            binding.mottram, binding.haitram, binding.namtram
        )

        var selectedLinearLayout: View? = null

        linearLayoutList.forEachIndexed { index, linearLayoutId ->
            val linearLayout = findViewById<View>(linearLayoutId)
            val textView = textViewList[index]

            linearLayout.setOnClickListener {
                selectedLinearLayout?.setBackgroundColor(Color.parseColor("#FFFFFF"))

                linearLayout.setBackgroundResource(R.drawable.muathe)

                selectedLinearLayout = linearLayout
                val textValue = textView.text.toString()
                stn = textValue.replace(",000$", "").toDoubleOrNull()!!

                binding.st.text = stn.toString() + "$"
            }
        }
    }


    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && data?.data != null) {
            val contactUri = data.data;
            val crContacts = contentResolver.query(contactUri!!, null, null, null, null);
            crContacts!!.moveToFirst()
            val id = crContacts.getString(crContacts.getColumnIndex(ContactsContract.Contacts._ID));
            if (Integer.parseInt(crContacts.getString(crContacts.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                val crPhones =
                    contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + " = ?",
                        arrayOf(id),
                        null)
                crPhones!!.moveToFirst()
                var phoneNo = crPhones.getString(crPhones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                crPhones.close()

                binding.sdt.setText(phoneNo.trim())
                sdt = phoneNo
            }
            crContacts.close()
        }


    }

}
