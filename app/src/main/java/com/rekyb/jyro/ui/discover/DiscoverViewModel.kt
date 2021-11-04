package com.rekyb.jyro.ui.discover

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.domain.use_case.remote.SearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
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

        viewModelScope.launch {
            search(query)
                .flowOn(Dispatchers.IO)
                .distinctUntilChanged()
                .collect { _dataState.value = dataState.value.copy(result = it) }

            _query.value = query
        }
    }
}
