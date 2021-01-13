package com.example.kotlinflowwithretrofit.ui.main.model

import com.example.kotlinflowwithretrofit.ui.main.model.data_class.User
import com.example.kotlinflowwithretrofit.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class PostRepository {

    companion object {
        fun getPost(): Flow<Response<List<User>>> = flow {
            // while (true) {
            val response = ApiClient.api.getPost()
            emit(response)
            // delay(10000)  // We need refresh in every 10 second
            // }
        }.flowOn(Dispatchers.IO)
    }
}