package com.example.banking.Layout

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.banking.databinding.ActivityThongKeTaiSanBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class ThongKeTaiSan : AppCompatActivity() {
    private lateinit var binding : ActivityThongKeTaiSanBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityThongKeTaiSanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var sodu = HomePage.Sodus
        var tientraiphieu = HomePage.tienlaitraiphieu
        var tientietkiem = HomePage.tientang
        var tong = sodu + tientraiphieu + tientietkiem + HomePage.tongtk3
        var fm = tong.toInt()
        var fms = formatnumber(fm)
        var text = "Tổng tài sản \n" + " ${fms}$"

        Log.d("tongtk3", "Tienlai tp: ${HomePage.tongtk3}")

        var arr : ArrayList<PieEntry> = ArrayList()



        if(HomePage.tienlaitraiphieu != 0 ){
            arr.add(PieEntry(HomePage.tienlaitraiphieu.toFloat(),"Trái phiếu"))
        }
        if(HomePage.tientang.toInt() != 0){
            arr.add(PieEntry(HomePage.tientang.toFloat(),"Chứng chỉ bảo lộc"))
        }
        if(HomePage.tongtk3.toInt() != 0 ){
            arr.add(PieEntry(HomePage.tongtk3.toFloat(),"Vun đắp mục tiêu"))
        }

        arr.add(PieEntry(sodu.toFloat(),"Số dư"))
        val pieDataSet = PieDataSet(arr, "")

        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 255)
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 15f

        val pieData = PieData(pieDataSet)

        binding.Chart.data = pieData
       binding.Chart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        binding.Chart.legend.formSize = 18f
        binding.Chart.legend.form = Legend.LegendForm.CIRCLE
        binding.Chart.setCenterTextSize(20f)
        val cusText = Typeface.create("text", Typeface.BOLD)
        binding.Chart.setCenterTextTypeface(cusText)
        binding.Chart.description = null
        binding.Chart.setCenterTextColor(Color.BLACK)
        binding.Chart.centerText = "${text}"
        binding.Chart.animateY(2000)


        binding.back.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

    }

    fun formatnumber(number: Int) : String{
        return String.format("%,d" , number)
    }
}