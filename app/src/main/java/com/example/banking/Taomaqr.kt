package com.example.banking

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.banking.Layout.HomePage
import com.example.banking.databinding.ActivityTaomaqrBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix

class Taomaqr : AppCompatActivity() {
    private lateinit var binding : ActivityTaomaqrBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaomaqrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.sdt.text = HomePage.sdts
        binding.tentk.text = HomePage.names

        try {
            val bitMatrix: BitMatrix = MultiFormatWriter().encode(
                HomePage.sdts,
                BarcodeFormat.QR_CODE,
                700,
                700,
                null
            )

            // Chuyển BitMatrix thành Bitmap
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            Log.d("huhu", "onViewCreated: ${bmp}")

            binding.qrImageView.setImageBitmap(bmp)
        } catch (e: WriterException) {
            e.printStackTrace()
            Log.d("huhu", "getinfo: ${e}")
        }
    }
}