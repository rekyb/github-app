package com.rekyb.jyro.domain.model

data class UserDetailsModel(
    val id: Int?,
    val userName: String?,
    val fullName: String?,
    val userAvatarUrl: String?,
    val userCompany: String?,
    val userLocation: String?,
    val followersUrl: String?,
    val followingUrl: String?,
)
