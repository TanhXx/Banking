package com.example.banking

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import com.example.banking.databinding.BottomshetFeedbackBinding

class Bottomsheet_Rateus(context: Context) : BaseBottomSheet(context) {
    lateinit var binding : BottomshetFeedbackBinding

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BottomshetFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvCancel.setOnClickListener {
            dismiss()
        }

        binding.ratingbar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { _, rating, _ ->
            if(rating>0){
                binding.tvRateus.isEnabled = false
                binding.tvRateus.setTextColor(R.color.color_text_rateus)
                binding.tvRateus.setBackgroundResource(R.drawable.bg_btn_add_app)
                binding.tvCancel.visibility = View.VISIBLE
                binding.ivEdt.visibility = View.VISIBLE
                binding.tvWerearework.text = context.getString(R.string.oh_no)
                binding.weAre.text = context.getString(R.string.thanks_for)
                if(rating.toInt() in 1..2){
                    binding.animationView.setAnimation(R.raw.crying_flat)
                    binding.animationView.playAnimation()
                }else if (rating.toInt() == 3){
                    binding.animationView.setAnimation(R.raw.sad_flat)
                    binding.animationView.playAnimation()
                }else if(rating.toInt() == 4){
                    binding.animationView.setAnimation(R.raw.happy_flat)
                    binding.animationView.playAnimation()
                }else{
                    binding.animationView.setAnimation(R.raw.heart_eyes_flat)
                    binding.animationView.playAnimation()
                }
            }/*else{
                binding.tvRateus.isEnabled = true
                binding.tvRateus.setBackgroundResource(R.drawable.bg_button_rateus)
                binding.tvCancel.visibility = View.GONE
                binding.ivEdt.visibility = View.GONE
                binding.tvWerearework.text = context.getString(R.string.we_are_working)
                binding.weAre.text = context.getString(R.string.we_are)
            }*/
        }
    }

    fun checkShowBottomSheet() {
        if (!isShowing) {
            show()
        }
    }

    fun checkHideBottomSheet() {
        if (isShowing) {
            dismiss()
        }
    }
}