package com.rekyb.jyro.domain.model

data class SearchModel(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val userItems: List<UserItemsModel>,
)
