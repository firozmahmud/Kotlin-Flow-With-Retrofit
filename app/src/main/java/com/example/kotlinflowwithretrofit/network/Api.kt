package com.example.kotlinflowwithretrofit.network

import com.example.kotlinflowwithretrofit.ui.main.model.data_class.Post
import retrofit2.http.GET

interface Api {
    @GET("posts")
    suspend fun getPost(): List<Post>
}