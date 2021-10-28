package com.rekyb.jyro.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rekyb.jyro.common.Constants
import com.rekyb.jyro.domain.use_case.SearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val userSearch: SearchUserUseCase,
) : ViewModel() {

    private val _query = MutableStateFlow("")
    private val _dataState = MutableStateFlow(DiscoverState())

    val dataState get() = _dataState.asStateFlow()

    fun searchUser(query: String) {
        _dataState.value = dataState.value.copy(isDataOnLoad = true)

        if (query == _query.value) return

        viewModelScope.launch {
            delay(Constants.SEARCH_DELAY)

            userSearch(query)
                .flowOn(Dispatchers.IO)
                .collect { data ->
                    _dataState.value = dataState.value.copy(
                        isDataOnLoad = false,
                        result = data)
                }
        }

        _query.value = query
    }
}