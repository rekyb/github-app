package com.rekyb.jyro.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rekyb.jyro.domain.model.UserDetailsModel
import com.rekyb.jyro.domain.use_case.local.AddToFavUseCase
import com.rekyb.jyro.domain.use_case.local.CheckUserUseCase
import com.rekyb.jyro.domain.use_case.local.RemoveFromFavUseCase
import com.rekyb.jyro.domain.use_case.remote.GetDetailsUseCase
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
class ProfileViewModel @Inject constructor(
    private val getDetails: GetDetailsUseCase,
    private val addToFav: AddToFavUseCase,
    private val removeFromFav: RemoveFromFavUseCase,
    private val checkOnFav: CheckUserUseCase,
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState get() = _profileState.asStateFlow()

    fun getUserDetails(userName: String) {
        viewModelScope.launch {
            getDetails(userName)
                .flowOn(Dispatchers.IO)
                .distinctUntilChanged()
                .collect {
                    _profileState.value = profileState.value.copy(result = it)
                }
        }
    }

    fun checkIsUserOnFavList(userName: String) {
        viewModelScope.launch {
            checkOnFav.invoke(userName)
                .flowOn(Dispatchers.IO)
                .distinctUntilChanged()
                .collect {
                    _profileState.value =
                        profileState.value.copy(isUserListedAsFavourite = it)
                }
        }
    }

    fun addUserToFavList(user: UserDetailsModel) {
        viewModelScope.launch(Dispatchers.IO) { addToFav(user) }
    }

    fun removeUserFromFavList(user: UserDetailsModel) {
        viewModelScope.launch(Dispatchers.IO) { removeFromFav(user) }
    }
}
