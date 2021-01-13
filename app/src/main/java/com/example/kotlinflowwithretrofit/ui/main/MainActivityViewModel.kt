package com.example.kotlinflowwithretrofit.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinflowwithretrofit.model.data_class.Post
import com.example.kotlinflowwithretrofit.model.PostRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    var postData: MutableLiveData<List<Post>> = MutableLiveData()
    var postDataError: MutableLiveData<String> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        getPostData()
    }

    private fun getPostData() {
        viewModelScope.launch {

            isLoading.value = true

            PostRepository.getPost()
                .catch { error ->
                    isLoading.value = false
                    postDataError.value = error.localizedMessage
                }
                .collect { data ->
                    postData.value = data
                    isLoading.value = false
                }

        }
    }
}
