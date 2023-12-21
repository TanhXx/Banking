package com.example.banking

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.banking.databinding.ActivityFeedBackBinding

class FeedBack : AppCompatActivity() {
    lateinit var binding: ActivityFeedBackBinding
    private var listFeedBack: ArrayList<FeedBack> = ArrayList()
    private var TAG = "RateAppFragment"
    private var textEditText: String = ""
    private var totalLength : Int = 0


    companion object {
        const val EMAIL = "Tuananh25102002p@gmail.com"
        const val SUBJECT = "FeedBack"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFeedBackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edtInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    val trimmedString = s.trim()
                    textEditText = trimmedString.toString()
                    totalLength = trimmedString.length

                }

                if (totalLength >= 6) {
                    binding.btnSubmit.setBackgroundResource(R.drawable.bg_buttom_submit)
                    binding.btnSubmit.isEnabled = true
                } else {
                    binding.btnSubmit.setBackgroundResource(R.drawable.bg_submit_null)
                    binding.btnSubmit.isEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.btnSubmit.setOnClickListener {



            val selectorIntent = Intent(Intent.ACTION_SENDTO)
            val urlString =
                "mailto:" + Uri.encode(EMAIL) + "?subject=" + Uri.encode(SUBJECT) + "&body=" + Uri.encode(
                    textEditText
                )
            selectorIntent.data = Uri.parse(urlString)

            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(EMAIL))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, SUBJECT)
            emailIntent.putExtra(Intent.EXTRA_TEXT, textEditText)
            emailIntent.selector = selectorIntent

            startActivity(Intent.createChooser(emailIntent, "Send email"))
        }
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }


    }
}