package com.example.kotlinflowwithretrofit.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinflowwithretrofit.R
import com.example.kotlinflowwithretrofit.common.Result
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

        viewModel.postLiveData.observe(this, { result ->

            when (result) {
                is Result.Loading -> handleProgressBar(result.isLoading)
                is Result.Error -> handleErrorResponse(result.errorMessage)
                is Result.Success -> handleSuccessResponse(result.data)
            }

        })
    }

    private fun handleProgressBar(isLoading: Boolean) {
        when (isLoading) {
            true -> showProgressBar(progressBar)
            false -> hideProgressBar(progressBar)
        }
    }

    private fun handleErrorResponse(errorMessage: String) {
        showEmptyView(emptyView)
        showToast(errorMessage, true)
    }

    private fun handleSuccessResponse(data: Any) {
        val dataList = data as List<Post>
        showData(dataList)
    }

    private fun showData(data: List<Post>) {
        hideEmptyView(emptyView)
        rvRecycler.adapter = DataAdapter(data)
    }

}