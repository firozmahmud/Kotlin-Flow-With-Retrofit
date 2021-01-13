package com.example.kotlinflowwithretrofit.ui.user_list

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinflowwithretrofit.R
import com.example.kotlinflowwithretrofit.common.Result
import com.example.kotlinflowwithretrofit.ui.user_list.model.data_class.User
import com.example.kotlinflowwithretrofit.ui.BaseActivity
import com.example.kotlinflowwithretrofit.ui.user_list.adapter.UserAdapter
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : BaseActivity() {

    private val TAG = "MainActivity"
    private lateinit var viewModel: UserActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        initComponent()
        observeLiveData()
    }

    private fun initComponent() {
        hideView(progressBar)
        hideView(emptyView)

        viewModel = ViewModelProvider(this)[UserActivityViewModel::class.java]
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

        if (dataList.isEmpty()) {
            showView(emptyView)
            return
        }
        showData(dataList)
    }

    private fun showData(data: List<User>) {
        hideView(emptyView)
        showView(rvRecycler)
        rvRecycler.adapter = UserAdapter(data)
    }

}