package com.rekyb.jyro.ui.favourites

import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.domain.model.UserDetailsModel

data class FavouritesState(
    val result: DataState<List<UserDetailsModel>>? = null,
)
