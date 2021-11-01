package com.rekyb.jyro.data.remote.response

import com.squareup.moshi.Json

data class GetDetailsResponseDto(
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "login") val login: String?,
    @field:Json(name = "avatar_url") val userAvatarUrl: String?,
    @field:Json(name = "company") val company: String?,
    @field:Json(name = "location") val location: String?,
    @field:Json(name = "followers_url") val followersUrl: String?,
    @field:Json(name = "following_url") val followingUrl: String?,
)