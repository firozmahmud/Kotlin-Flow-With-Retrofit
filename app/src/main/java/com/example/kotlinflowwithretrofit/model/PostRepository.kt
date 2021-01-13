package com.example.kotlinflowwithretrofit.model

import com.example.kotlinflowwithretrofit.model.data_class.Post
import com.example.kotlinflowwithretrofit.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PostRepository {

    companion object {
        fun getPost(): Flow<List<Post>> = flow {
            val response = ApiClient.api.getPost()
            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}