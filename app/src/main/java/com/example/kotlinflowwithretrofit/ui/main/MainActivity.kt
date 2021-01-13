package com.example.kotlinflowwithretrofit.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinflowwithretrofit.R
import com.example.kotlinflowwithretrofit.common.Result
import com.example.kotlinflowwithretrofit.ui.main.model.data_class.User
import com.example.kotlinflowwithretrofit.ui.BaseActivity
import com.example.kotlinflowwithretrofit.ui.main.adapter.DataAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val TAG = "MainActivity"
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponent()
        observeLiveData()
    }

    private fun initComponent() {
        hideView(progressBar)
        hideView(emptyView)

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
            true -> showView(progressBar)
            false -> hideView(progressBar)
        }
    }

    private fun handleErrorResponse(errorMessage: String) {
        showView(emptyView)
        hideView(rvRecycler)
        showToast(errorMessage, true)
    }

    private fun handleSuccessResponse(data: Any) {
        val dataList = data as List<User>
        showData(dataList)
    }

    private fun showData(data: List<User>) {
        hideView(emptyView)
        showView(rvRecycler)
        rvRecycler.adapter = DataAdapter(data)
    }

}