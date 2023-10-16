package com.example.banking.Layout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.banking.databinding.BottomsheetTraiphieuBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BottomSheetTraiPhieu : BottomSheetDialogFragment() {
    var soluong : Int = 0
    var sodu = HomePage.Sodus.toInt()
    var tien : Int = 0
    private lateinit var binding : BottomsheetTraiphieuBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomsheetTraiphieuBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var sodus = formatDoubleWithCommas(sodu)
        binding.sodu.text = "SD: "+ sodus.toString() + "S"



       getsoluong()
        Log.d("soluongtp", "Số lượng: ${soluong} ")

        binding.thanhtoan.setOnClickListener {
            if(HomePage.timechenhlechtp > 0){
                Toast.makeText(requireContext(), "Mỗi cá nhân chỉ được mua trái phiếu 1 lần", Toast.LENGTH_SHORT).show()
            }

            if (sodu<tien){
                Toast.makeText(requireContext(), "Số dư không đủ", Toast.LENGTH_SHORT).show()
            }
            else{
                val timegui = Date()
                val datefm = SimpleDateFormat("HH:mm - dd/MM/yyyy", Locale.getDefault())
                val time = datefm.format(timegui)

               var tienconlai = sodu - tien
                var info = hashMapOf("sodu" to tienconlai, "tientraiphieu" to tien, "timebatdauguitp" to time)

                db.collection("Users").document(HomePage.Userid)
                    .update(info as Map<String, Any>)



                var intent = Intent(requireContext(), Success::class.java)
                startActivity(intent)
            }
        }



    }

    private fun getsoluong() {
        var min = 100
        var max =1000
        val step = 100
        val value = (min..max step step).map { it.toString() }.toTypedArray()
        binding.sl.maxValue = value.size - 1
        binding.sl.minValue = 0
        binding.sl.value = 100
        binding.sl.displayedValues = value
        binding.sl.setOnScrollListener { numberPicker, i ->
            soluong = value[numberPicker.value].toInt()
            var tiendukien = soluong * 1
            var sodus = formatDoubleWithCommas(tiendukien)
            binding.sodu.text = "Tiền Dự Kiến  " + "${sodus}" + "$"
            tien = soluong * 10

        }
    }
    fun formatDoubleWithCommas(number: Int): String {
        return String.format("%,d", number)
    }


}