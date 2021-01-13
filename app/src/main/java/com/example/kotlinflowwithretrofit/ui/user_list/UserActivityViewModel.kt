package com.example.kotlinflowwithretrofit.ui.user_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinflowwithretrofit.common.NetworkResult
import com.example.kotlinflowwithretrofit.ui.user_list.model.UserRepository
import com.example.kotlinflowwithretrofit.ui.user_list.model.data_class.User
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response

class UserActivityViewModel : ViewModel() {

    var userListLiveData: MutableLiveData<NetworkResult> = MutableLiveData()

    init {
        getUserList()
    }

    private fun getUserList() {

        viewModelScope.launch {

            userListLiveData.value = NetworkResult.Loading(true)

            UserRepository.getUsers()
                .catch { error ->
                    userListLiveData.value = NetworkResult.Loading(false)
                    error.localizedMessage?.let { NetworkResult.Error(it) }
                }
                .collect { response ->
                    userListLiveData.value = NetworkResult.Loading(false)
                    handleGetUserListResponse(response)
                }
        }

    }


    private fun handleGetUserListResponse(response: Response<List<User>>) {
        if (response.isSuccessful && !response.body().isNullOrEmpty()) {
            userListLiveData.value = response.body()?.let { NetworkResult.Success(it) }
        } else {
            userListLiveData.value =
                NetworkResult.Error("Message : ${response.message()} , Code : ${response.code()}")
        }
    }
}
