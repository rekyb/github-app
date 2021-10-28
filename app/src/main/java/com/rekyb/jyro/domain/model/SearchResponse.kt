package com.rekyb.jyro.domain.model

data class SearchResponse(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val userItems: List<UserItems>,
)