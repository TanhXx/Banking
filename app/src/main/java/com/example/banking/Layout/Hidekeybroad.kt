package com.example.banking.Layout

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class Hidekeybroad : AppCompatActivity() {


    fun hidekeybroadfrag(fragment: Fragment){
        val view = fragment.requireActivity().currentFocus
        if (view != null){
            val iim = fragment.requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            iim.hideSoftInputFromWindow(view.windowToken,0)
        }
    }
}
