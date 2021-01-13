package com.example.kotlinflowwithretrofit.common

sealed class NetworkResult {
    class Loading(val isLoading: Boolean) : NetworkResult()
    class Error(val errorMessage: String) : NetworkResult()
    class Success(val data: Any) : NetworkResult()
}