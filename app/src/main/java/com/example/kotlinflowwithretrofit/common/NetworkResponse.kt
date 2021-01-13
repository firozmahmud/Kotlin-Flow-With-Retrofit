package com.example.kotlinflowwithretrofit.common

sealed class NetworkResponse {
    class Loading(val isLoading: Boolean) : NetworkResponse()
    class Error(val errorMessage: String) : NetworkResponse()
    class Success(val data: Any) : NetworkResponse()
}