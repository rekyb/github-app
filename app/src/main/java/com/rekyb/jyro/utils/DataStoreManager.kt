package com.rekyb.jyro.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DataStoreManager @Inject constructor(@ApplicationContext context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")
    private var appDataStore = context.dataStore

    suspend fun saveStringData(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        appDataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    suspend fun getStringData(key: String): String {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = appDataStore.data.first()

        return preferences[dataStoreKey] ?: "Use device theme"
    }
}