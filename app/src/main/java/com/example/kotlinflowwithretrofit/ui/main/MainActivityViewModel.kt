package com.example.kotlinflowwithretrofit.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinflowwithretrofit.common.Result
import com.example.kotlinflowwithretrofit.ui.main.model.PostRepository
import com.example.kotlinflowwithretrofit.ui.main.model.data_class.User
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivityViewModel : ViewModel() {

    var postLiveData: MutableLiveData<Result> = MutableLiveData()

    init {
        getPostData()
    }

    private fun getPostData() {

        viewModelScope.launch {

            postLiveData.value = Result.Loading(true)

            PostRepository.getPost()
                .catch { error ->
                    postLiveData.value = Result.Loading(false)
                    handleUserDataError(error)
                }
                .collect { response ->
                    handleUserDataSuccess(response)
                    postLiveData.value = Result.Loading(false)

                }
        }

    }

    private fun handleUserDataSuccess(response: Response<List<User>>) {
        if (response.isSuccessful) {
            postLiveData.value = response.body()?.let { Result.Success(it) }
        } else {
            postLiveData.value = Result.Error("Message : ${response.message()} , Code : ${response.code()}")
        }
    }

    private fun handleUserDataError(error: Throwable) {
        postLiveData.value = Result.Error(errorMessage = error.localizedMessage ?: "Unknown Error")
    }
}
