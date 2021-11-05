package com.rekyb.jyro.ui.follow

import com.rekyb.jyro.common.Resources
import com.rekyb.jyro.domain.model.UserItemsModel

data class FollowersState(
    val result: Resources<List<UserItemsModel>>? = null
)
