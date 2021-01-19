package com.example.kotlinflowwithretrofit.ui.user_list

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.kotlinflowwithretrofit.R
import com.example.kotlinflowwithretrofit.common.NetworkResult
import com.example.kotlinflowwithretrofit.data_store.LocalDataStore
import com.example.kotlinflowwithretrofit.ui.user_list.model.data_class.User
import com.example.kotlinflowwithretrofit.ui.BaseActivity
import com.example.kotlinflowwithretrofit.ui.user_list.adapter.UserAdapter
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserActivity : BaseActivity() {

    private val TAG = "MainActivity"
    private lateinit var viewModel: UserActivityViewModel
    private lateinit var userAdapter: UserAdapter

    private lateinit var localDataStore: LocalDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        localDataStore = LocalDataStore(this)

        GlobalScope.launch {
            localDataStore.storeData("users", "Hello world")
        }

        localDataStore.getData("users").asLiveData().observe(this@UserActivity, Observer {

            Log.d(TAG, "onCreate: ${it.toString()}")
        })


        initComponent()
        observeLiveData()
    }

    private fun initComponent() {
        hideView(progressBar)
        hideView(emptyView)
        userAdapter = UserAdapter()
        rvRecycler.adapter = userAdapter
        viewModel = ViewModelProvider(this)[UserActivityViewModel::class.java]
    }

    private fun observeLiveData() {

        viewModel.userListLiveData.observe(this, { result ->
            when (result) {
                is NetworkResult.Loading -> handleProgressBar(result.isLoading)
                is NetworkResult.Error -> handleErrorResponse(result.errorMessage)
                is NetworkResult.Success -> handleSuccessResponse(result.data)
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
            hideView(rvRecycler)
            return
        }
        showData(dataList)
    }

    private fun showData(data: List<User>) {
        hideView(emptyView)
        showView(rvRecycler)
        userAdapter.updateList(data)
    }

}