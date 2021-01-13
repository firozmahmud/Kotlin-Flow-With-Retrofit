package com.example.kotlinflowwithretrofit.ui.user_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinflowwithretrofit.common.NetworkResponse
import com.example.kotlinflowwithretrofit.ui.user_list.model.UserRepository
import com.example.kotlinflowwithretrofit.ui.user_list.model.data_class.User
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response

class UserActivityViewModel : ViewModel() {

    var userListLiveData: MutableLiveData<com.example.kotlinflowwithretrofit.common.NetworkResponse> =
        MutableLiveData()

    init {
        getUserList()
    }

    private fun getUserList() {

        viewModelScope.launch {

            userListLiveData.value = NetworkResponse.Loading(true)

            UserRepository.getUsers()
                .catch { error ->
                    userListLiveData.value = NetworkResponse.Loading(false)
                    handleGetUserListError(error)
                }
                .collect { response ->
                    userListLiveData.value = NetworkResponse.Loading(false)
                    handleGetUserListResponse(response)
                }
        }

    }

    private fun handleGetUserListResponse(response: Response<List<User>>) {
        if (response.isSuccessful) {
            if (response.body() == null) {
                userListLiveData.value = NetworkResponse.Error("Body is null")
            } else {
                userListLiveData.value = NetworkResponse.Success(response.body()!!)
            }
        } else {
            userListLiveData.value =
                NetworkResponse.Error("Message : ${response.message()} , Code : ${response.code()}")
        }
    }

    private fun handleGetUserListError(error: Throwable) {
        userListLiveData.value =
            NetworkResponse.Error(errorMessage = error.localizedMessage ?: "Unknown Error")
    }
}
