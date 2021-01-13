package com.example.kotlinflowwithretrofit.network

import com.example.kotlinflowwithretrofit.ui.user_list.model.data_class.User
import retrofit2.Response
import retrofit2.http.GET

interface UserApi {
    @GET("users")
    suspend fun getPost(): Response<List<User>>
}