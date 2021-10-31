package com.rekyb.jyro.ui.profile

import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.domain.model.GetDetailsModel

data class ProfileState(
    val result: DataState<GetDetailsModel>? = null
)
