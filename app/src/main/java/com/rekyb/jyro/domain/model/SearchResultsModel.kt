package com.rekyb.jyro.domain.model

data class SearchResultsModel(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val userItems: List<UserItemsModel>,
)
