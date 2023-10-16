package com.example.banking.Layout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.banking.databinding.BottomsheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetCT : BottomSheetDialogFragment() {
   private lateinit var binding: BottomsheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomsheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toinguoikhac.setOnClickListener {
                /*parentFragmentManager.beginTransaction().replace(R.id.framgent_container_aaa, FormChuyenTien()).commit()*/
            var intent = Intent(requireContext(),Chuyentien::class.java)
            startActivityForResult(intent,1)
            dismiss()
        }



    }


}