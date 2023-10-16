package com.example.banking.Layout

import android.app.DatePickerDialog
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.banking.R
import com.example.banking.databinding.FragmentDatlichoffBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Datlichoff : Fragment() {
    var checkdv =true
    private lateinit var binding: FragmentDatlichoffBinding
    private lateinit var selectedDate: Calendar
    private lateinit var textgio : String
    private lateinit var textngay : String
    private lateinit var textspiner : String
    private lateinit var dbrf : DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = FragmentDatlichoffBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.gridlayout.visibility = View.GONE
        binding.hvt.visibility = View.GONE
        binding.sdt.visibility = View.GONE
        binding.next.visibility = View.GONE
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setOnClickListener {
            showDatePicker()
        }

        dbrf = FirebaseDatabase.getInstance().getReference()

        changecolor()
        regisService()
        backNext()
    }

    private fun regisService(){
        val loaidichvu = resources.getStringArray(R.array.lichhen)
        val adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            loaidichvu
        )
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {


                when(p2){
                    0-> {
                        if(checkdv){
                            checkdv = false
                        }else{
                            Toast.makeText(requireContext(), "Vui lòng chọn các dịch vụ", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else -> {
                        // Lấy giá trị của mục được chọn
                        val selectedItem = p0?.getItemAtPosition(p2) as? String
                        if (selectedItem != null) {
                            textspiner = selectedItem
                            Log.d("textspiner" , textspiner)
                        }
                    }
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }
    private fun showDatePicker() {
        val toolbar = binding.toolbar
        toolbar.title = ""
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        selectedDate = Calendar.getInstance()
        val year = selectedDate.get(Calendar.YEAR)
        val month = selectedDate.get(Calendar.MONTH)
        val day = selectedDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = sdf.format(selectedDate.time)
                binding.textintb.text = formattedDate
                textngay = formattedDate
                Log.d("timetext", textngay)
            },
            year, month, day
        )
        datePickerDialog.show()

        binding.gridlayout.visibility = View.VISIBLE
        binding.hvt.visibility = View.VISIBLE
        binding.sdt.visibility = View.VISIBLE
        binding.next.visibility = View.VISIBLE



    }

    private fun backNext(){
        binding.imagereturn.setOnClickListener(View.OnClickListener {
            var fragment = requireActivity().supportFragmentManager
            fragment.popBackStack()

        })

        binding.next.setOnClickListener {
            var db = Firebase.firestore
            var textten = binding.hvt.text.toString()
            var textsdt = binding.sdt.text.toString()
            if (textspiner.isNullOrEmpty() || textngay.isNullOrEmpty() || textgio.isNullOrEmpty() || textten.isNullOrEmpty() || textsdt.isNullOrEmpty() ){
                Toast.makeText(requireContext(), "Thông tin phải điền đẩy đủ", Toast.LENGTH_SHORT).show()
                Log.d("error", "backNext: ")
            }else   {
            val docref = db.collection("LichHen").document(binding.hvt.text.toString()+" "+binding.sdt.text.toString())
            var info = hashMapOf(
                "Dịch vụ" to textspiner,
                "Ngày Đặt lịch" to textngay,
                "Họ Tên" to binding.hvt.text.toString(),
                "SDT" to binding.sdt.text.toString(),
                "Thời Gian: " to textgio
            )

            docref.set(info).addOnSuccessListener {
                Toast.makeText(requireContext(), "OK", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Faild", Toast.LENGTH_SHORT).show()
            }
                parentFragmentManager.beginTransaction().replace(R.id.holder,Login()).commit()
            }

        }
    }

    private fun changecolor(){
        val buttonList = listOf(
            binding.tambamuoi, binding.chinkhong, binding.chinbamuoi, binding.muoi,
            binding.muoibamuoi, binding.muoimot, binding.muoimotbamuoi, binding.muoihai,
            binding.muoihaibamuoi, binding.muoiba, binding.muoibabamuoi, binding.muoibon)


        var selectedButton: Button? = null

        buttonList.forEach { button ->
            button.setOnClickListener {
                // Đặt lại màu cho nút trước đó (nếu có)
                selectedButton?.setBackgroundColor(Color.parseColor("#58D8E6"))

                // Đổi màu nền của nút hiện tại
                button.setBackgroundColor(Color.WHITE)


                selectedButton = button
                textgio = button.text.toString()
                Log.d("textchange", textgio)
            }
        }

    }

}
