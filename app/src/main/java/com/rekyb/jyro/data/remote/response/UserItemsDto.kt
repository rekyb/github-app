package com.rekyb.jyro.data.remote.response

import com.squareup.moshi.Json

class UserItemsDto(
    @field:Json(name = "login") val login: String,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "avatar_url") val avatarUrl: String,
    @field:Json(name = "url") val url: String,
)
