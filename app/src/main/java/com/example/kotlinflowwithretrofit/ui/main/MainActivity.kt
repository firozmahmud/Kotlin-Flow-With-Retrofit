package com.example.kotlinflowwithretrofit.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinflowwithretrofit.R
import com.example.kotlinflowwithretrofit.ui.main.model.data_class.Post
import com.example.kotlinflowwithretrofit.ui.BaseActivity
import com.example.kotlinflowwithretrofit.ui.main.adapter.DataAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponent()
        observeLiveData()
    }

    private fun initComponent() {
        hideProgressBar(progressBar)
        hideEmptyView(emptyView)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    private fun observeLiveData() {

        viewModel.isLoading.observe(this, Observer { isLoading ->
            when (isLoading) {
                true -> showProgressBar(progressBar)
                false -> hideProgressBar(progressBar)
            }
        })

        viewModel.postDataError.observe(this, Observer { errorMessage ->
            showEmptyView(emptyView)
            showToast(errorMessage, true)
        })

        viewModel.postData.observe(this, Observer { data ->
            showData(data)
        })
    }

    private fun showData(data: List<Post>) {
        hideEmptyView(emptyView)
        rvRecycler.adapter = DataAdapter(data)
    }


}