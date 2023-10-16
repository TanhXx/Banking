package com.example.banking.Layout

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.banking.TientietkiemService
import com.example.banking.databinding.FragmentHomePageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Locale


class HomePage : Fragment() {
    var sum = 0.0
    var isCompleted = false
    var stoimg = FirebaseStorage.getInstance()
    private var checksdt = ""
    val db = Firebase.firestore
    val auth = FirebaseAuth.getInstance()

    private lateinit var binding: FragmentHomePageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getinfo()


        noti()




        binding.chuyentien.setOnClickListener {
            val bottomsheetfragment = BottomSheetCT()
            bottomsheetfragment.show(parentFragmentManager,bottomsheetfragment.tag)
        }
        binding.napthe.setOnClickListener {
            var intent = Intent(requireContext(), Napthe::class.java)
            startActivity(intent)
        }
        binding.vaytien.setOnClickListener {
            var intent = Intent(requireContext(),Vaytien::class.java)
            startActivity(intent)
        }
        binding.tietkiem.setOnClickListener {
            var intent = Intent(requireContext(),Tietkiem::class.java)
            startActivityForResult(intent,1)
        }
        binding.thongke.setOnClickListener {
            var intent = Intent(requireContext(),ThongKeTaiSan::class.java)
            startActivity(intent)
        }

        binding.Tkvt.setOnClickListener {
            var intent = Intent(requireContext(), TaiKhoanvaThe::class.java)
            startActivity(intent)
        }

        var intent = Intent(requireContext(),TientietkiemService::class.java)
        requireActivity().startService(intent)


     /*   var imageView = binding.gifback

        Glide.with(this).load(com.example.banking.R.drawable.gifbackchuyentien)
            .into(imageView)

*/

    }

    companion object{

        var TongCks : Double = 0.0
        var Sodus : Double = 0.0
        var Tientietkiem : Double = 0.0
        var Userid : String = ""
        var tientang : Double = 0.0
        var timebatdaugui : String = ""
        var timedangnhap : String = ""
        var timechenhlech : Int = 0
        var timechenhlechtp : Int = 1
        var tientraiphieu : Int = 0
        var tienlaitraiphieu : Int = 0
        var tongtk3 = 0.0
    }


    private fun getinfo() {
        val UserID = auth.currentUser!!.uid

        Userid = UserID
        val docRef = db.collection("Users").document(UserID)
        val filename = "${UserID}.jpg"
        val storeRef = stoimg.reference.child("Image").child(UserID!!).child(filename)

        storeRef.getBytes(1324 * 1324).addOnSuccessListener { byte ->

            val bitmaps = BitmapFactory.decodeByteArray(byte, 0, byte.size)
            binding.anh.setImageBitmap(bitmaps)
        }




        docRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                // Xử lý lỗi nếu có
                Log.e("Getinfo", "lỗi: $error")
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                val names = snapshot.getString("name")
                val sdts = snapshot.getString("SDT")
                val ages = snapshot.getString("date")
                val sodus = snapshot.getDouble("sodu")
                val tongcks = snapshot.getDouble("Tongck")
                val tientietkiems = snapshot.getDouble("Tientietkiem")
                val tientangs = snapshot.getDouble("tientang")
                val timeguis = snapshot.getString("timebatdaugui")
                val timedangnhaps = snapshot.getString("timedangnhap")
                val tientraiphieus = snapshot.getDouble("tientraiphieu")
                val tienlaitraphieus = snapshot.getDouble("tienlaitraiphieu")
                val timeguitps = snapshot.getString("timebatdauguitp")
                val tongtk3s = snapshot.getDouble("tongtk3")



                binding.stk.setOnClickListener {
                    val textcopy = sdts.toString()
                    val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clipData = ClipData.newPlainText("text",textcopy)
                    clipboardManager.setPrimaryClip(clipData)
                    Toast.makeText(requireContext(), "sao chép thành công", Toast.LENGTH_SHORT).show()
                }

                tongtk3 = tongtk3s!!
               /* if(tientietkiems!! > 0){
                    var intent = Intent(requireContext(),TientietkiemService::class.java)
                    requireContext().startService(intent)
                }*/

                val collectionRef = db.collection("TietKiem").document(Userid).collection("TietKiem")



                collectionRef.get().addOnSuccessListener { documentSnapshot ->


                    for (document in documentSnapshot.documents) {
                        val docreff = collectionRef.document(document.id)
                        var ngaygui = document.getString("ngaygui")
                        val datefm = SimpleDateFormat("HH:mm - dd/MM/yyyy", Locale.getDefault())
                        val tiengui = document.getString("tiengui")!!.toIntOrNull()

                        var timedn = hashMapOf(
                            "timedangnhap" to timedangnhaps,
                        )
                        docreff.update(timedn as Map<String, Any>)

                        var date1 = datefm.parse(timedangnhaps)
                        var date2 = datefm.parse(ngaygui)

                        var timechenhlech = (date1.time - date2.time) / (24 * 60 * 60 * 1000)

                        var timecl = hashMapOf("timechenhlech" to timechenhlech)
                        docreff.update(timecl as Map<String, Any>)



                        var gocvalai = tiengui!! + ((tiengui * 0.000082)) * timechenhlech

                        val updategvl = hashMapOf("Gocvalai" to gocvalai)
                        docreff.update(updategvl as Map<String,Any>)
                             sum += gocvalai

                       /* Log.d("sum", "getinfo: ${sum}")*/

                    }
                    if(!isCompleted){
                        val info = hashMapOf(
                            "tongtk3" to sum
                        )
                        db.collection("Users").document(auth.uid!!)
                            .update(info as Map<String, Any>)

                        isCompleted = true
                    }

                }




                val datefm =  SimpleDateFormat("HH:mm - dd/MM/yyyy" , Locale.getDefault())
// tính time chênh lệch giữa gửi tiết kiệm thường
               var timechot : Int = 0
                if(timeguis!= ""){
                     val date1 = datefm.parse(timedangnhaps)
                val date2 = datefm.parse(timeguis)
                val time = (date1.time - date2.time )/ (1000*60*60*24)
                val timefm = time.toInt()
                    timechot = timefm
                }
                // time chênh lệch tp
                var timechottp : Int = 0
                if(timeguitps!= ""){
                    val date1 = datefm.parse(timedangnhaps)
                    val date2 = datefm.parse(timeguitps)
                    val time = (date1.time - date2.time )/ (1000*60*60*24)
                    val timefm = time.toInt()
                    timechottp = timefm
                }



                timechenhlechtp = timechottp


                timechenhlech = timechot
                var info = hashMapOf("timechenhlech" to timechenhlech,
                    "timechenhlechtp" to timechenhlechtp,

                )
                docRef.update(info as Map<String, Any>)


                timebatdaugui = timeguis!!
                timedangnhap = timedangnhaps!!
                tienlaitraiphieu = tienlaitraphieus!!.toInt()
                tientraiphieu = tientraiphieus!!.toInt()

                Log.d("laitp", "getinfo: ${tientraiphieu}")
                TongCks = tongcks!!
                Sodus = sodus!!
                Tientietkiem = tientietkiems!!
                tientang = tientangs!!

                binding.name.text = names

                var textedit = sdts!!.substring(sdts!!.length - 4)
                binding.stk.text = "•••• •••• •••• "+textedit
                binding.date.text = ages


                var fm = Sodus.toInt()
                var fms = formatDoubleWithCommas(fm)


                binding.sodu.text = fms + "$"


            } else {
                Log.d("getinfo", "Dữ liệu không tồn tại")
            }
        }
    }
    fun formatDoubleWithCommas(number: Int): String {
        return String.format("%,d", number)
    }

    private fun noti(){
        binding.notification.setOnClickListener {
           var intent = Intent(requireContext(), Notification::class.java)
            startActivity(intent)
        }
    }




}


