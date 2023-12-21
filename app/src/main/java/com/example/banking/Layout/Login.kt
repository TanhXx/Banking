package com.example.banking.Layout


import Register
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.RECEIVER_NOT_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import androidx.fragment.app.Fragment
import com.example.banking.R
import com.example.banking.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Login : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    var isPasswordVisiable = false
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appBroadcast = AppBroadcast()
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(requireContext(),appBroadcast, intentFilter,RECEIVER_NOT_EXPORTED)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hidekeybroad()
        auth = FirebaseAuth.getInstance()
        /*var callbackManager = CallbackManager.Factory.create()*/



       /* language()*/
        calendar()
        /*binding.loginfb.setOnClickListener {
            binding.loginfb.setOnClickListener {
                // Gọi phương thức đăng nhập của Facebook
                LoginManager.getInstance().logInWithReadPermissions(this, listOf("email", "public_profile"))
                LoginManager.getInstance().registerCallback(callbackManager, object :
                    FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        // Đăng nhập thành công, nhận được token từ Facebook
                        val accessToken = loginResult.accessToken

                        // Sử dụng token để đăng nhập vào Firebase Authentication
                        val credential = FacebookAuthProvider.getCredential(accessToken.token)
                        FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Xác thực thành công
                                    val user = FirebaseAuth.getInstance().currentUser
                                    // Tiếp tục xử lý hoặc hiển thị thông báo đăng nhập thành công
                                } else {
                                    // Xác thực thất bại, xử lý thông báo lỗi hoặc thực hiện các hành động khác
                                }
                            }
                    }

                    override fun onCancel() {
                        // Người dùng hủy đăng nhập
                    }

                    override fun onError(exception: FacebookException) {
                        // Xảy ra lỗi khi đăng nhập
                    }
                })
            }

        }*/




        view.setOnClickListener {
            val hidekeybroads = Hidekeybroad()
            hidekeybroads.hidekeybroadfrag(this)
        }


        binding.btnlogin.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            var tk = binding.edtTk.text.toString().trim()
            var mk = binding.edtMk.text.toString().trim()

            if(tk.isEmpty() && mk.isEmpty()){
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
            else {
                if (tk.equals("admin@gmail.com") && mk.equals("huhuhu")){
                    auth.signInWithEmailAndPassword("admin@gmail.com", "huhuhu")
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                /*parentFragmentManager.beginTransaction().replace(R.id.holder,MainAdmin()).commit()*/
                                var intent = Intent(requireContext(),MainAdmin()::class.java)
                                startActivity(intent)
                                Toast.makeText(requireContext(), "ID: "+ auth.currentUser?.uid, Toast.LENGTH_SHORT).show()

                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }else{
                    auth.signInWithEmailAndPassword(tk, mk)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                parentFragmentManager.beginTransaction().replace(R.id.holder,Main()).commit()
                                Toast.makeText(requireContext(), "ID: "+ auth.currentUser?.uid, Toast.LENGTH_SHORT).show()
                                    var currenttime = Date()
                                    var dateFormat = SimpleDateFormat("HH:mm - dd/MM/yyyy", Locale.getDefault())
                                    val time = dateFormat.format(currenttime)
                                val info = hashMapOf(
                                    "timedangnhap" to time
                                )
                                db.collection("Users").document(auth.currentUser!!.uid)
                                    .update(info as Map<String,Any>)
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


        }

        binding.mk.setEndIconOnClickListener {
            isPasswordVisiable =! isPasswordVisiable
            if (isPasswordVisiable){
                binding.edtMk.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                        android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }else{
                binding.edtMk.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                        android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            binding.edtMk.text?.let { it1 -> binding.edtMk.setSelection(it1.length) }
        }

        binding.motk.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.holder,Register()).addToBackStack(null).commit()
        }
        binding.quenmk.setOnClickListener {
            if(binding.edtTk.text!!.isEmpty()){
                Toast.makeText(requireContext(), "Bạn chưa nhập tài khoản", Toast.LENGTH_SHORT).show()
            }else{
                Log.d("huhu", "onViewCreated: OK")
                auth.sendPasswordResetEmail(binding.edtTk.text.toString())
                    .addOnCompleteListener { task ->
                        Log.d("huhu", "onViewCreated: ok")
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(), "Vui lòng kiểm tra hộp thư email đã nhập!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Email không tồn tại!", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            }

    }


    fun hidekeybroad(){
        val activiti = requireActivity()
        val view = activiti.currentFocus
        if(view!= null){
            val imm = activiti.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken,0)

        }
    }
    @SuppressLint("MissingInflatedId")
    private fun calendar() {
        binding.datlich.setOnClickListener {
            val dialogbinding = layoutInflater.inflate(R.layout.fragment_datlich, null)

            val mydialog = Dialog(requireActivity())
            mydialog.setContentView(dialogbinding)


            mydialog.setCancelable(true)
            mydialog.setCanceledOnTouchOutside(true)

            val btnok = dialogbinding.findViewById<Button>(R.id.btnhuy)
            val btnol = dialogbinding.findViewById<TextView>(R.id.tructuyen)
            val btnoff = dialogbinding.findViewById<TextView>(R.id.tructiep)

            btnok.setOnClickListener {
                mydialog.dismiss()
            }

            btnol.setOnClickListener {
                mydialog.dismiss()
                val uri = Uri.parse("tel:0383865954")
                val intent = Intent(Intent.ACTION_DIAL, uri)
                startActivity(intent)
            }

            btnoff.setOnClickListener {
                mydialog.dismiss()
                parentFragmentManager.beginTransaction().replace(R.id.holder, Datlichoff()).addToBackStack(null).commit()
            }

            mydialog.show()
        }
    }

}