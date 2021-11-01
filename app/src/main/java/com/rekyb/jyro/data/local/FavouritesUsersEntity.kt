package com.rekyb.jyro.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rekyb.jyro.domain.model.UserDetailsModel

@Entity(tableName = "favourites_table")
data class FavouritesUsersEntity(
    @PrimaryKey val id: Int,
    val login: String,
    val name: String,
    val avatar_url: String,
    val company: String,
    val location: String,
    val followers_url: String,
    val following_url: String,
) {
    companion object {
        fun from(model: UserDetailsModel): FavouritesUsersEntity {
            return FavouritesUsersEntity(
                id = model.id!!,
                login = model.userName!!,
                name = model.fullName ?: "",
                avatar_url = model.userAvatarUrl ?: "",
                company = model.userCompany ?: "",
                location = model.userLocation ?: "",
                followers_url = model.followersUrl!!,
                following_url = model.followingUrl!!
            )
        }
    }

    fun toModel(): UserDetailsModel {
        return UserDetailsModel(
            id,
            login,
            name,
            avatar_url,
            company,
            location,
            followers_url,
            following_url
        )
    }
}