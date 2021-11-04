package com.rekyb.jyro.ui.more

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rekyb.jyro.common.Constants
import com.rekyb.jyro.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {

    private val _themeState: MutableLiveData<String> = MutableLiveData()

    fun setTheme(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.saveStringData(Constants.THEME_KEY, value)
        }
    }

    fun getTheme(key: String): String {
        viewModelScope.launch(Dispatchers.IO) {
            val result = dataStoreManager.getStringData(key)
            _themeState.postValue(result)
        }

        return _themeState.value ?: "Use device theme"
    }
}