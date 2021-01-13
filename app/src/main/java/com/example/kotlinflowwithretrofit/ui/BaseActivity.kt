package com.example.kotlinflowwithretrofit.ui

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    fun showView(view: View) {
        view.visibility = VISIBLE
    }

    fun hideView(view: View) {
        view.visibility = GONE
    }

    fun showToast(message: String, isLong: Boolean = false) {
        val duration = when (isLong) {
            false -> Toast.LENGTH_SHORT
            true -> Toast.LENGTH_LONG
        }
        Toast.makeText(applicationContext, message, duration).show()
    }
}