package com.rekyb.jyro.ui.discover

import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.domain.model.SearchResponse

data class DiscoverState(
    val isDataOnLoad: Boolean = false,
    val result: DataState<SearchResponse>? = null
)