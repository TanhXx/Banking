package com.example.banking.Layout

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.banking.databinding.ActivityTaiKhoanvaTheBinding
import com.google.zxing.BinaryBitmap
import com.google.zxing.LuminanceSource
import com.google.zxing.MultiFormatReader
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.io.IOException

class TaiKhoanvaThe : AppCompatActivity() {
    private lateinit var binding : ActivityTaiKhoanvaTheBinding
    private lateinit var codeScanner: CodeScanner
    private var tempImageUri: Uri? = null
    private var tempImagePath: String = ""
    private var bitmapScan: Bitmap? = null

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                tempImageUri = uri
              /*  tempImagePath = UriUtils.getPathFromUriTryMyBest(this, tempImageUri)*/
//                Glide.with(requireContext()).load(tempImageUri).diskCacheStrategy(
//                    DiskCacheStrategy.ALL)
//                    .skipMemoryCache(true).into(binding.ivGalleryQr)

                bitmapScan?.recycle()
                bitmapScan = uriToBitmap(uri)
                bitmapScan?.let { scanQRCodeFromImage(it) }

            } else {
                //TODO show toast
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaiKhoanvaTheBinding.inflate(layoutInflater)
        setContentView(binding.root)

        codeScanner = CodeScanner(this, binding.scannerView)

        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true

        codeScanner.decodeCallback = DecodeCallback {

            this.runOnUiThread {
                getDataFromQr(it.text)
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            this.runOnUiThread {

            }
        }

        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }


        binding.imgBack.setOnClickListener {
            /*closeFragment(this)*/
            onBackPressed()
        }



        binding.imgAlbum.setOnClickListener {
           /* WrapAdsResume.instance.disableAdsResume()*/
            galleryLauncher.launch("image/*")
        }



    }
    private fun uriToBitmap(uri: Uri): Bitmap? {
        try {
            val inputStream = this.contentResolver.openInputStream(uri)
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun scanQRCodeFromImage(bitmap: Bitmap) {
        val width = bitmap.width
        val height = bitmap.height
        val pixels = IntArray(width * height)

        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        val source: LuminanceSource = RGBLuminanceSource(width, height, pixels)
        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))

        val reader = MultiFormatReader()
        try {
            val result = reader.decode(binaryBitmap)
            // Xử lý kết quả sau khi quét được mã QR.
            val qrCodeData = result.text
            // In mã QR đã quét

            getDataFromQr(qrCodeData)
            // replaceFragment(ResultScanQrCodeFragment.instance(qrCodeData))
        } catch (e: Exception) {
            // Xử lý lỗi nếu không thể quét được mã QR.
            Toast.makeText(this, "Không tìm thấy mã QR", Toast.LENGTH_SHORT).show()
            /*showDialogValid()*/
            e.message?.let { Log.e("QR Code Error", it) }
        }
    }

    private fun getDataFromQr(id: String) {
        var intent = Intent(this, Chuyentien::class.java)
        intent.putExtra("ID_KEY", id) // Gửi biến id sang activity Chuyentien
        Log.d("huhu", "getDataFromQr: ${id}")
        startActivity(intent)
    }


    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }


}