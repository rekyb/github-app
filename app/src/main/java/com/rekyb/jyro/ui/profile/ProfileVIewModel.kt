package com.rekyb.jyro.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.domain.use_case.GetDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProfileVIewModel @Inject constructor(
    private val getDetails: GetDetailsUseCase,
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState get() = _profileState.asStateFlow()

    fun getUserDetails(userName: String) {
        getDetails.invoke(userName)
            .map { _profileState.value = profileState.value.copy(result = it) }
            .flowOn(Dispatchers.IO)
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                DataState.Loading
            )
    }

}