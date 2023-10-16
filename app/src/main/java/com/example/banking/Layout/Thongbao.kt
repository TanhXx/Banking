package com.example.banking.Layout

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banking.Adapter.lichsuAdapter
import com.example.banking.Data.lichsuGd
import com.example.banking.databinding.FragmentThongbaoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Thongbao : Fragment() {
    private var ds :ArrayList<lichsuGd> = ArrayList()
    var db = Firebase.firestore
    var auth = FirebaseAuth.getInstance()
    var UserID = auth.currentUser?.uid
lateinit var binding : FragmentThongbaoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = FragmentThongbaoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcv.layoutManager = LinearLayoutManager(requireContext())
        binding.rcv.adapter = lichsuAdapter(this,ds)

        val docref = UserID?.let { db.collection("Users").document(it) }
        Toast.makeText(requireContext(), UserID.toString(), Toast.LENGTH_SHORT).show()
        if (docref != null) {
            docref.get().addOnSuccessListener {data ->
                if(data.exists()){
                    val list = data.get("banking") as MutableList<String>
                    if(list != null){
                        for (list in list){
                            val lsgd = lichsuGd(list)
                            ds.add(lsgd)
                            ds.reverse()

                        }

                        binding.rcv.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    }

}