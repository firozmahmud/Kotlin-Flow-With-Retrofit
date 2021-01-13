package com.example.kotlinflowwithretrofit.common

sealed class Result {
    class Loading(val isLoading: Boolean) : Result()
    class Error(val errorMessage: String) : Result()
    class Success(val data: Any) : Result()
}