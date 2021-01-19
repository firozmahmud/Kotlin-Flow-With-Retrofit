package com.example.kotlinflowwithretrofit.ui.user_list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinflowwithretrofit.App
import com.example.kotlinflowwithretrofit.common.NetworkResult
import com.example.kotlinflowwithretrofit.data_store.LocalDataStore
import com.example.kotlinflowwithretrofit.ui.user_list.model.UserRepository
import com.example.kotlinflowwithretrofit.ui.user_list.model.data_class.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.Response
import java.nio.file.attribute.UserPrincipalLookupService

class UserActivityViewModel : ViewModel() {

    var userListLiveData: MutableLiveData<NetworkResult> = MutableLiveData()

    init {
        getUserList()
    }

    private fun getUserList() {

        viewModelScope.launch {

            userListLiveData.value = NetworkResult.Loading(true)

            UserRepository.getUsers()
                .onEach {
                    saveToLocalCache(it.body())
                }
                .catch { error ->
                    userListLiveData.value = NetworkResult.Loading(false)
                    //error.localizedMessage?.let { NetworkResult.Error(it) }
                    getDataFromCache("users")
                }
                .collect { response ->
                    userListLiveData.value = NetworkResult.Loading(false)
                    handleGetUserListResponse(response)
                }
        }

    }

    private fun handleGetUserListResponse(response: Response<List<User>>) {
        if (response.isSuccessful && !response.body().isNullOrEmpty()) {
            userListLiveData.value = response.body()?.let { NetworkResult.Success(it) }
        } else {
            userListLiveData.value =
                NetworkResult.Error("Message : ${response.message()} , Code : ${response.code()}")
        }
    }

    private fun saveToLocalCache(body: List<User>?) {
        val dataStore = App.context?.let { LocalDataStore(context = it.applicationContext) }

        viewModelScope.launch {
            dataStore?.storeData("users", "This is a local data")
        }
    }


    private fun getDataFromCache(key: String) {
        val dataStore = App.context?.let { LocalDataStore(context = it.applicationContext) }


        val data = dataStore?.getData(key)

        var users: List<User> = ArrayList()

        val gson = Gson()

        users = listOf(gson.fromJson(data.toString(), User::class.java))

        NetworkResult.Success(users)

        Log.d("DataStore", "getDataFromCache: ${data.toString()}")

    }


}

