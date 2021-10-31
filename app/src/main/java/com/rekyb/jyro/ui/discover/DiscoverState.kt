package com.rekyb.jyro.ui.discover

import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.domain.model.SearchModel

data class DiscoverState(
    val result: DataState<SearchModel>? = null
)
