package com.rekyb.jyro.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.rekyb.jyro.common.Constants
import com.rekyb.jyro.common.Themes
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : DataStoreRepository {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Constants.APP_PREF)
    private var appDataStore = context.dataStore

    private object PreferencesKeys {
        val THEME_KEY = stringPreferencesKey(Constants.THEME_KEY)
    }

    override val themeSelection: Flow<String>
        get() = appDataStore.getValue(PreferencesKeys.THEME_KEY, Themes.SYSTEM_DEFAULT.selection)

    override suspend fun setAppTheme(selection: String) {
        appDataStore.setValue(PreferencesKeys.THEME_KEY, selection)
    }

    override suspend fun clearAppPreferenceCache() {
        appDataStore.edit { it.clear() }
    }

    private suspend fun <T> DataStore<Preferences>.setValue(
        key: Preferences.Key<T>,
        value: T,
    ) {
        this.edit { preferences ->
            preferences[key] = value
        }
    }

    private fun <T> DataStore<Preferences>.getValue(
        key: Preferences.Key<T>,
        defaultValue: T,
    ): Flow<T> {
        return this.data.catch { error ->
            if (error is IOException) {
                emit(emptyPreferences())
            } else {
                throw error
            }
        }.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }
}
