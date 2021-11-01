package com.rekyb.jyro.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites_table")
data class UserItemsModel(
    @PrimaryKey
    val userName: String,
    val userId: Int,
    val userAvatarUrl: String,
    val userProfileUrl: String,
)
