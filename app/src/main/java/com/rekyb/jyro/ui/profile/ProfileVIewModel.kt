package com.rekyb.jyro.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.domain.model.UserDetailsModel
import com.rekyb.jyro.domain.use_case.local.AddToFavUseCase
import com.rekyb.jyro.domain.use_case.local.CheckUserUseCase
import com.rekyb.jyro.domain.use_case.local.RemoveFromFavUseCase
import com.rekyb.jyro.domain.use_case.remote.GetDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileVIewModel @Inject constructor(
    private val getDetails: GetDetailsUseCase,
    private val addToFavUseCase: AddToFavUseCase,
    private val removeFromFavUseCase: RemoveFromFavUseCase,
    private val checkUserUseCase: CheckUserUseCase,
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState get() = _profileState.asStateFlow()

    fun getUserDetails(userName: String) {
        getDetails.invoke(userName)
            .map { _profileState.value = profileState.value.copy(result = it) }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                DataState.Loading
            )
    }

    fun addUserToFavList(user: UserDetailsModel) {
        viewModelScope.launch(Dispatchers.IO) { addToFavUseCase(user) }
    }

    fun removeUserFromFavList(user: UserDetailsModel) {
        viewModelScope.launch(Dispatchers.IO) { removeFromFavUseCase(user) }
    }

    fun checkIsUserOnFavList(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            checkUserUseCase(userId)
                .flowOn(Dispatchers.IO)
                .collect {
                    _profileState.value = profileState.value.copy(isUserListedAsFavourite = it)
                }
        }
    }
}