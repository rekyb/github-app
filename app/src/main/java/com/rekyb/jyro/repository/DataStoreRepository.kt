package com.rekyb.jyro.repository

import kotlinx.coroutines.flow.Flow


interface DataStoreRepository {

    val themeSelection: Flow<String>
    suspend fun setAppTheme(selection: String)
    suspend fun clearAppPreferenceCache()

}