package com.example.kotlinflowwithretrofit.data_store

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataStore(context: Context) {

    private val dataStore = context.createDataStore(name = "user_prefs")


    suspend fun storeData(key: String, value: String) {
        dataStore.edit {
            it[getKey(key)] = value
        }
    }


    fun getData(key: String): Flow<String> = dataStore.data.map {
        it[getKey(key)] ?: ""
    }

    private fun getKey(key: String): Preferences.Key<String> {
        return stringPreferencesKey(key)
    }

}
