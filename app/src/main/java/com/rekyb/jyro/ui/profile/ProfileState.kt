package com.rekyb.jyro.ui.profile

import com.rekyb.jyro.common.Resources
import com.rekyb.jyro.domain.model.UserDetailsModel

data class ProfileState(
    val isUserListedAsFavourite: Boolean? = false,
    val result: Resources<UserDetailsModel>? = null
)
