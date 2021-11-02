package com.rekyb.jyro.ui.profile

import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.domain.model.UserDetailsModel

data class ProfileState(
    val isUserListedAsFavourite: Boolean? = false,
    val result: DataState<UserDetailsModel>? = null
)
