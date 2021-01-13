package com.example.kotlinflowwithretrofit.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinflowwithretrofit.common.Result
import com.example.kotlinflowwithretrofit.ui.main.model.PostRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
                    postLiveData.value =
                        Result.Error(errorMessage = error.localizedMessage ?: "Unknown Error")
                }
                .collect { data ->
                    if (data.isNullOrEmpty()) {
                        postLiveData.value = Result.Error("No data found")
                    } else {
                        postLiveData.value = Result.Success(data)
                    }
                    postLiveData.value = Result.Loading(false)

                }
        }

    }
}
