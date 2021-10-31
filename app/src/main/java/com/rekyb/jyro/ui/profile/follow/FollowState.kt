package com.rekyb.jyro.ui.profile.follow

import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.domain.model.UserItemsModel

data class FollowState(
    val followingResult: DataState<List<UserItemsModel>>? = null,
    val followersResult: DataState<List<UserItemsModel>>? = null
)
