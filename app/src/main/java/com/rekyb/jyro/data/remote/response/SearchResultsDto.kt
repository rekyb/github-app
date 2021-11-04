package com.rekyb.jyro.data.remote.response

import com.squareup.moshi.Json

data class SearchResultsDto(
    @field:Json(name = "total_count") val totalCount: Int,
    @field:Json(name = "incomplete_results") val incompleteResults: Boolean,
    @field:Json(name = "items") val items: List<UserItemsDto>,
)
