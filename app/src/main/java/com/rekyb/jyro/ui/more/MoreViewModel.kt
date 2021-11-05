package com.rekyb.jyro.ui.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rekyb.jyro.domain.use_case.data_store.ClearAppPrefUseCase
import com.rekyb.jyro.domain.use_case.data_store.SetThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val setTheme: SetThemeUseCase,
    private val clearAppPref: ClearAppPrefUseCase
) : ViewModel() {

    fun saveSelectedAppTheme(selection: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setTheme(selection)
        }
    }

    fun clearAppPref() {
        viewModelScope.launch(Dispatchers.IO) {
            clearAppPref.invoke()
        }
    }
}