package com.rekyb.jyro.ui.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rekyb.jyro.domain.use_case.remote.GetFollowersUseCase
import com.rekyb.jyro.domain.use_case.remote.GetFollowingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor(
    private val getFollowing: GetFollowingsUseCase,
    private val getFollowers: GetFollowersUseCase,
) : ViewModel() {

    private val _followingState = MutableStateFlow(FollowingState())
    private val _followersState = MutableStateFlow(FollowersState())

    val followingState get() = _followingState.asStateFlow()
    val followersState get() = _followersState.asStateFlow()

    fun getFollowingList(userName: String) {
        viewModelScope.launch {
            getFollowing(userName)
                .flowOn(Dispatchers.IO)
                .collect { result ->
                    _followingState.value = followingState.value.copy(result = result)
                }
        }
    }

    fun getFollowersList(userName: String) {
        viewModelScope.launch {
            getFollowers(userName)
                .flowOn(Dispatchers.IO)
                .collect { result ->
                    _followersState.value = followersState.value.copy(result = result)
                }
        }
    }
}