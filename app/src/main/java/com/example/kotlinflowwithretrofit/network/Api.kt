package com.example.kotlinflowwithretrofit.network

import com.example.kotlinflowwithretrofit.ui.main.model.data_class.User
import retrofit2.http.GET

interface Api {
    @GET("users")
    suspend fun getPost(): List<User>
}