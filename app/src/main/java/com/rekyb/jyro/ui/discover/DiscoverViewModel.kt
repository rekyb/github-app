package com.rekyb.jyro.ui.discover

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.domain.use_case.SearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val search: SearchUserUseCase,
) : ViewModel() {

    private val _query = MutableStateFlow("")
    private val _dataState = MutableStateFlow(DiscoverState())

    val dataState get() = _dataState.asStateFlow()
    var scrollState: Parcelable? = null

    fun searchUser(query: String) {
        val isErrorEncountered = _dataState.value.result is DataState.Error

        if (query == _query.value && !isErrorEncountered) return

        search(query)
            .map { _dataState.value = dataState.value.copy(result = it) }
            .flowOn(Dispatchers.IO)
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                DataState.Loading
            )

        _query.value = query
    }
}
