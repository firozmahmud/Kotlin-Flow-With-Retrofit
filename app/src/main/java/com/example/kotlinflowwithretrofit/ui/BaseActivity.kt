package com.example.kotlinflowwithretrofit.ui

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    fun showProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.GONE
    }


    fun showEmptyView(emptyView: TextView) {
        emptyView.visibility = View.VISIBLE
    }

    fun hideEmptyView(emptyView: TextView) {
        emptyView.visibility = View.GONE
    }

    fun showToast(message: String, isLong: Boolean = false) {
        val duration = when (isLong) {
            false -> Toast.LENGTH_SHORT
            true -> Toast.LENGTH_LONG
        }
        Toast.makeText(applicationContext, message, duration).show()
    }
}