package com.rekyb.jyro.ui.favourites

import com.rekyb.jyro.common.Resources
import com.rekyb.jyro.domain.model.UserDetailsModel

data class FavouritesState(
    val result: Resources<List<UserDetailsModel>>? = null,
)
