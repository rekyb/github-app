package com.rekyb.jyro.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.domain.use_case.SearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class DiscoverViewModel @Inject constructor(
    private val search: SearchUserUseCase,
) : ViewModel() {

    private val _query = MutableStateFlow("")
    private val _dataState = MutableStateFlow(DiscoverState())
    val dataState get() = _dataState.asStateFlow()

    fun searchUser(query: String) {
        if (query == _query.value) return

        search.invoke(query)
            .map { _dataState.value = dataState.value.copy(result = it) }
            .flatMapLatest { _dataState }
            .flowOn(Dispatchers.IO)
            .stateIn(viewModelScope, SharingStarted.Eagerly, DataState.Loading)

        _query.value = query
    }
}
