package com.example.banking.Layout

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.banking.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream


class Profile : Fragment() {

    val db = Firebase.firestore
    val auth = FirebaseAuth.getInstance()

    var sto = Firebase.storage
    var stodown = FirebaseStorage.getInstance()
    lateinit var imageBitmap : Bitmap
    lateinit var binding : FragmentProfileBinding

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            imageBitmap = result.data?.extras?.get("data") as Bitmap
            val capturedImage = imageBitmap
            val imageViewAnh = binding.viewuser

            imageViewAnh.setImageBitmap(capturedImage)
            setinfo(capturedImage)


        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       /* binding.viewpager2home.adapter = tablayoutAdapter(this)
        binding.viewpager2home.orientation = ViewPager2.ORIENTATION_HORIZONTAL*/



        binding.hotro.setOnClickListener {
            var intent = Intent(requireContext(),Screen::class.java)
            startActivityForResult(intent,234)
        }

        getinfo()
      /*  tablayout()*/


        binding.viewuser.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(cameraIntent)
        }

        binding.thongtincanhan.setOnClickListener {
            var intent = Intent(requireContext(),Info::class.java)
            startActivityForResult(intent,123)
        }
        binding.logout.setOnClickListener {
           var buidler = AlertDialog.Builder(requireContext())

            buidler.setTitle("Đăng xuất")
            buidler.setMessage("Bạn có muốn đăng xuất không ? ")
            buidler.setNegativeButton("Có"){dialog, id ->
       FirebaseAuth.getInstance().signOut()
                Toast.makeText(requireContext(), "Đã out", Toast.LENGTH_SHORT).show()
                    var intent = Intent(requireContext(),Splash::class.java)
                startActivity(intent)
            }
            buidler.setPositiveButton("Không"){dialog, id ->
                dialog.dismiss()
            }
            var dialog : AlertDialog = buidler.create()
            dialog.show()

        }

    }

    /*private fun tablayout() {
        TabLayoutMediator(binding.tablayout, binding.viewpager2home){tab, position ->
            when(position){
                0 -> {
                    tab.text = "Personal"
                   
                }

                1 -> tab.text = "Cards"
            }

        }.attach()
    }*/
    private fun getinfo(){
        var UserID = auth.currentUser!!.uid
        val fileName = "${UserID}.jpg"
        val docref = db.collection("Users").document(UserID)
        val storef = stodown.reference.child("Image").child(UserID).child(fileName)


        storef.getBytes(1024*1024).addOnSuccessListener {byte ->
            val bitmap = BitmapFactory.decodeByteArray(byte,0,byte.size)
            binding.viewuser.setImageBitmap(bitmap)
            Log.d("Getinfoprofire",  "load ảnh ok")
        }.addOnFailureListener {
            Log.d("Getinfoprofire",  "load ảnh lỗi")
        }

        docref.get().addOnSuccessListener {data ->
            if(data.exists()){
                val names = data.getString("name")
                binding.tvName.text = names

            }else{
                Log.d("Getinfoprofire",  "Lấy ra name lỗi")
            }
        }
    }

    private fun setinfo(bitmap: Bitmap){
        var UserID = auth.currentUser!!.uid
        val fileName = "${UserID}.jpg"
        val stodocref = sto.reference.child("Image").child(UserID).child(fileName)
        // chuyển đổi bitmap thành mảng byte
        val byteArrayOutputStream = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val data = byteArrayOutputStream.toByteArray()
        // tải mảng byte lên cloud
        stodocref.putBytes(data).addOnSuccessListener {
            Toast.makeText(requireContext(), "Ảnh đã đc cập nhật", Toast.LENGTH_SHORT).show()
            Log.d("setinfoprofire",  "cập nhật ảnh ok")
        }.addOnFailureListener{it
            Log.d("setinfoprofire",  "lỗi cập nhật ảnh ${it}")
        }





    }


}