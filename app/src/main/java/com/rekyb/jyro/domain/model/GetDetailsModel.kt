package com.rekyb.jyro.domain.model

data class GetDetailsModel(
    val fullName: String?,
    val userName: String?,
    val userAvatarUrl: String?,
    val userCompany: String?,
    val userLocation: String?,
    val followersUrl: String?,
    val followingUrl: String?,
)