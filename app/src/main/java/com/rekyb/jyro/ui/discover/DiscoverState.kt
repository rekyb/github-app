package com.rekyb.jyro.ui.discover

import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.domain.model.SearchResultsModel

data class DiscoverState(
    val result: DataState<SearchResultsModel>? = null
)
