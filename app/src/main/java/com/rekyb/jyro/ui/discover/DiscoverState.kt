package com.rekyb.jyro.ui.discover

import com.rekyb.jyro.common.Resources
import com.rekyb.jyro.domain.model.SearchResultsModel

data class DiscoverState(
    val result: Resources<SearchResultsModel>? = null
)
