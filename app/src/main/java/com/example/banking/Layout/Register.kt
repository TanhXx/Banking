
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.banking.Layout.Hidekeybroad
import com.example.banking.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Calendar
import java.util.Date
import kotlin.random.Random


class Register : Fragment() {
    var tien = Random.nextInt(50,1000)
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var dbrf : DatabaseReference
    val firestore = Firebase.firestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        dbrf = FirebaseDatabase.getInstance().getReference()


        // Write a message to the database

        binding.btnregister.setOnClickListener {
            register()


        }

        binding.back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.register.setOnClickListener {
            Hidekeybroad().hidekeybroadfrag(this)
        }
    }


    private fun register(){
        var gmail = binding.edtTk.text.toString()
        var mk = binding.edtMk.text.toString()
        var cfmk = binding.edtCfmk.text.toString()
        var name = binding.edtName.text.toString()
        var age = binding.edtAge.text.toString()
        var sdt = binding.edtSdt.text.toString()

        if(mk == cfmk && mk.length in 6..10 &&
            !TextUtils.isEmpty(name) && !TextUtils.isEmpty(age) &&
            !TextUtils.isEmpty(sdt) && age.toIntOrNull() != null &&
            sdt.toIntOrNull() != null && age.toInt() > 18){
            auth.fetchSignInMethodsForEmail(gmail).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val result = task.result
                    if (result?.signInMethods?.isEmpty() == true){
                        // check sdt
                        firestore.collection("Users").whereEqualTo("SDT",sdt)
                            .get().addOnCompleteListener { task ->
                                if(task.isSuccessful ){
                                    val result = task.result
                                    Log.d("TAG", result.toString())
                                    if(!result.isEmpty){
                                        Toast.makeText(requireContext(), "Đã có sdt", Toast.LENGTH_SHORT).show()
                                    }else{
                                        auth.createUserWithEmailAndPassword(gmail, mk)
                                        .addOnCompleteListener(requireActivity()) { task ->
                                            if (task.isSuccessful) {
                                                Toast.makeText(requireContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show()
                                                var date = Date()
                                                val calendar = Calendar.getInstance()
                                                calendar.time = date

                                                val thang = calendar.get(Calendar.MONTH) + 1
                                                val nam = calendar.get(Calendar.YEAR) + 4
                                                var hsd = thang.toString() + "/" + nam.toString()

                                                if (auth.currentUser?.uid != null ){
                                                    var userID = auth.currentUser!!.uid
                                                    var db = Firebase.firestore
                                                    val docref = db.collection("Users").document(userID)
                                                    var gmail = binding.edtTk.text.toString()

                                                   /* val banking = hashMapOf(
                                                        "Thông báo" to "",
                                                    )*/

                                                    val info = hashMapOf(
                                                        "timebatdaugui" to "",
                                                        "timechenhlechtp" to "",
                                                        "timebatdauguitp" to "",
                                                        "tienlaitraiphieu" to 0,
                                                        "tientraiphieu" to 0,
                                                        "timedangnhap" to "",
                                                        "name" to name,
                                                        "age" to age,
                                                        "tongtk3" to 0,
                                                        "gmail" to gmail,
                                                        "SDT" to sdt,
                                                        "Tongck" to 0,
                                                        "id" to userID,
                                                        "sodu" to tien,
                                                        "timechenhlech" to 0,
                                                        "Tientietkiem" to 0,
                                                        "tientang" to 0,
                                                        "banking" to mutableListOf<String>(),
                                                        "date" to hsd)
                                                    docref.set(info).addOnSuccessListener {
                                                    }.addOnFailureListener {
                                                    }

                                                }else   {
                                                    Toast.makeText(requireContext(), "default", Toast.LENGTH_SHORT).show()
                                                }
                                                requireActivity().supportFragmentManager.popBackStack()

                                            } else {
                                                Toast.makeText(
                                                    requireContext(),
                                                    "Authentication failed.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }

                                    }
                                }
                                else{

                                    Toast.makeText(requireContext(), "Lỗi khong ton tai", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                    else{
                        Toast.makeText(requireContext(), "Gmail này đã tồn tại", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }else{
            Toast.makeText(requireContext(), "Mật khẩu từ 6-10 kí tự, tuổi phải lớn hơn 18 ", Toast.LENGTH_SHORT).show()
        }
    }


}




