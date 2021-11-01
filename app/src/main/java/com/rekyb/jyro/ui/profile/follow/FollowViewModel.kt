package com.rekyb.jyro.ui.profile.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.domain.use_case.remote.GetFollowersUseCase
import com.rekyb.jyro.domain.use_case.remote.GetFollowingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor(
    private val getFollowing: GetFollowingsUseCase,
    private val getFollowers: GetFollowersUseCase,
) : ViewModel() {

    private val _followState = MutableStateFlow(FollowState())
    val followState get() = _followState.asStateFlow()

    fun getFollowingList(userName: String) {
        getFollowing.invoke(userName)
            .map { _followState.value = followState.value.copy(followingResult = it) }
            .flowOn(Dispatchers.IO)
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                DataState.Loading
            )
    }

    fun getFollowersList(userName: String) {
        getFollowers.invoke(userName)
            .map { _followState.value = followState.value.copy(followersResult = it) }
            .flowOn(Dispatchers.IO)
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                DataState.Loading
            )
    }
}