package com.rekyb.jyro.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.domain.use_case.SearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val userSearch: SearchUserUseCase,
) : ViewModel() {

    private var searchJob: Job? = null

    private val _dataState = MutableStateFlow(DiscoverState())
    val dataState get() = _dataState.asStateFlow()

    fun searchUser(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {

            _dataState.value = dataState.value.copy(
                isDataOnLoad = true,
                isStandby = false
            )

            delay(700L)

            userSearch(query)
                .flowOn(Dispatchers.IO)
                .collect { data ->
                    _dataState.value = dataState.value.copy(
                        isDataOnLoad = false,
                        result = data
                    )
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }

}