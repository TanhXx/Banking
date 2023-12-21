package com.example.banking.Layout

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.banking.Data.lichsuGd
import com.example.banking.databinding.ActivityChuyentienBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Chuyentien : AppCompatActivity() {
    var db = Firebase.firestore
    var auth = FirebaseAuth.getInstance()
    var stoimg = FirebaseStorage.getInstance()
    var TongCk : Double = 0.0
    var sodunguoinhanchuagd : Double = 0.0
    val UserID = auth.currentUser!!.uid
    val docRef = db.collection("Users").document(UserID)
    var ds: ArrayList<lichsuGd> = ArrayList()
    lateinit var binding: ActivityChuyentienBinding
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.banking.R.layout.activity_chuyentien)
        binding = ActivityChuyentienBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nhận dữ liệu từ Intent
        val receivedIntent = intent
        val receivedId = receivedIntent.getStringExtra("ID_KEY")

        // Kiểm tra xem có dữ liệu không null trước khi sử dụng
        if (receivedId != null) {
            // Sử dụng dữ liệu nhận được ở đây
            Log.d("Received ID", receivedId)
            binding.sotknguoinhan.setText(receivedId)
            // Ví dụ: Hiển thị ID trong logcat
        }

        binding.back.setOnClickListener {
         /*  supportFragmentManager.beginTransaction().replace(com.example.banking.R.id.holderct  , HomePage()).commit()*/
            setResult(Activity.RESULT_OK)
            finish()
        }

        var imageView : ImageView = findViewById(com.example.banking.R.id.gifback)

        Glide.with(this).load(com.example.banking.R.drawable.gifbackchuyentien)
            .into(imageView)

        getData()
        chuyentien()


        binding.ct.setOnClickListener {
            hidekeybroadactivity(it)
        }

    }

    fun hidekeybroadactivity(view : View){
        if(view!= null){
            val iim = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            iim.hideSoftInputFromWindow(view.windowToken,0)
        }
    }


    companion object{
         var Tongckdevay : Double = 0.0

    }


    private fun getData() {
        val filename = "${UserID}.jpg"
        val storeRef = stoimg.reference.child("Image").child(UserID).child(filename)

        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                // Xử lý lỗi nếu có
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val names = snapshot.getString("name")
                val stk = snapshot.getString("SDT")
                val sodus = snapshot.getDouble("sodu")
                val tongcks = snapshot.getDouble("Tongck")
                TongCk = tongcks!!
                Tongckdevay = TongCk
                binding.sotk.text = stk
                binding.tentk.text = names

                var fm = sodus!!.toInt()
                var fms = formatDoubleWithCommas(fm)

                binding.soduvi.text = fms +"$"
            }
        }
        storeRef.getBytes(1324 * 1324).addOnSuccessListener { byte ->
            val bitmap = BitmapFactory.decodeByteArray(byte, 0, byte.size)
            binding.avatar.setImageBitmap(bitmap)
            Log.d("GetData",  "load ảnh ok")
        }.addOnFailureListener {
            // Xử lý lỗi khi tải ảnh
            Log.d("GetData",  "load ảnh lỗi")
        }
    }
    fun formatDoubleWithCommas(number: Int): String {
        return String.format("%,d", number)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun chuyentien(){
        binding.chuyen.setOnClickListener {
            val soTkNguoiNhan = binding.sotknguoinhan.text.toString()
            val soTien = binding.sotien.text.toString()
            val noiDungGD = binding.ndgd.text.toString()

            if (soTkNguoiNhan.isEmpty() || soTien.isEmpty() || noiDungGD.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            } else{
                    val currentDateTime = LocalDateTime.now()
                    val format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                    val formatDatetime = currentDateTime.format(format)
                    var stknhan = binding.sotknguoinhan.text.toString()
                Log.d("Chuyen tien", "Giá trị stknhan: $stknhan")

                db.collection("Users").whereEqualTo("SDT", stknhan)
                        .get().addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                var result = task.result
                                if(!result.isEmpty){
                                    val document = result.documents[0]
                                    val sdnguoinhan = document.getDouble("sodu")
                                    sodunguoinhanchuagd = sdnguoinhan!!
                                    // xử lí sk chuyển tiền
                                    var sodunguoinhanchuagds = sodunguoinhanchuagd.toString().toDoubleOrNull() // số dư người nhận chưa gd
                                    var sdnguoinhansaugd : Int = 0
                                    var sdnguoiguisaugd : Int = 0




                                    var soduviscv = binding.soduvi.text.toString() // số dư chủ tk
                                    var fm = soduviscv.replace(".","")
                                    var fms = fm.replace("$","")
                                   var soduvis = fms.toDoubleOrNull()
                                    var sotiens = binding.sotien.text.toString().toDoubleOrNull() // số tiền chuyển


                                    if(sotiens!! > soduvis!!){
                                        Toast.makeText(this, "Tài khoản không đủ để thực hiện giao dịch", Toast.LENGTH_SHORT).show()
                                    }else {

                                        var buidler = AlertDialog.Builder(this)
                                        buidler.setMessage("Bạn có muốn chuyển tiền không ? ")
                                        buidler.setTitle("Thông báo")
                                        buidler.setNegativeButton("Có"){dialog, id ->
                                            var intent = Intent(this@Chuyentien, Success::class.java)
                                            startActivity(intent)

                                            soduvis -= sotiens // số dư sau gd

                                            sdnguoinhansaugd =
                                                (sodunguoinhanchuagds!! + sotiens).toInt() // số dư người nhận sau gd

                                            val Tongcks = TongCk + sotiens

                                            Tongckdevay = Tongcks
                                            val docrefbanking = db.collection("Users").document(UserID)
                                            val infonguoigui = hashMapOf(
                                                "sodu" to soduvis,
                                                "Tongck" to Tongcks
                                                )

                                            docrefbanking.update(infonguoigui as Map<String, Any>)


                                            val docrefsd= db.collection("Users").document(UserID)
                                            val newBankingData = "Số tài khoản: ${binding.sotk.text} - ${sotiens}$ tới STK: ${stknhan}" +
                                                    ", lúc ${formatDatetime}, nội dung: ${noiDungGD}. Số dư cuối giao dịch: ${soduvis}$"
                                            docrefsd.update("banking", FieldValue.arrayUnion(newBankingData))


                                            Log.d("Chuyen tien",  "noi dung + ${noiDungGD}")
                                            db.collection("Users").whereEqualTo("SDT", stknhan)
                                                .get().addOnCompleteListener { task ->
                                                    if (task.isSuccessful) {
                                                        /*Toast.makeText(this, "Tong gd$tongGD", Toast.LENGTH_SHORT).show()*/
                                                        val result = task.result
                                                        val document = result.documents[0]
                                                        val idNguoinhan = document.id
                                                        val docrefss =
                                                            db.collection("Users").document(idNguoinhan)

                                                        val info = hashMapOf(
                                                            "sodu" to sdnguoinhansaugd
                                                        )
                                                        Toast.makeText(this, "Tong ck: ${Tongcks}", Toast.LENGTH_SHORT)
                                                            .show()
                                                        docrefss.update(info as Map<String, Any>)

                                                    } else {
                                                        Log.d("Chuyen tien",  "So sánh sdt để getdata lỗi")
                                                    }
                                                }
                                            val docrefnhan = db.collection("Users").whereEqualTo("SDT",stknhan)
                                            val newBankingDatas = "Số tài khoản: ${stknhan} + ${sotiens}$" +
                                                    ", lúc + ${formatDatetime}, nội dung: ${noiDungGD}. Số dư cuối giao dịch: ${sdnguoinhansaugd}$"
                                            docrefnhan.get().addOnSuccessListener { doc ->
                                                for (document in doc){
                                                    val docref = db.collection("Users").document(document.id)
                                                    docref.update("banking", FieldValue.arrayUnion(newBankingDatas))
                                                }
                                            }
                                        }
                                        buidler.setPositiveButton("Không, quay lại"){dialog, id ->
                                            dialog.dismiss()
                                        }

                                        val dialog : AlertDialog = buidler.create()
                                        dialog.show()


                                    }
                                    binding.sotknguoinhan.text.clear()
                                    binding.sotien.text.clear()
                                    binding.ndgd.text.clear()

                                }else{
                                    Toast.makeText(this, "Không tìm thấy stk", Toast.LENGTH_SHORT).show()
                                }

                            }
                            else{
                                Log.d("Chuyen tien", "chuyentien: Truy vấn thất bại")
                            }

                        }
                }


        }

    }

}