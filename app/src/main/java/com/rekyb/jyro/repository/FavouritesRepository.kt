package com.rekyb.jyro.repository

import com.rekyb.jyro.domain.model.UserDetailsModel

interface FavouritesRepository {

    suspend fun getFavouritesList(): List<UserDetailsModel>
    suspend fun checkUserOnFavList(userId: Int): Boolean
    suspend fun addUserToFavList(user: UserDetailsModel)
    suspend fun removeUserFromFavList(user: UserDetailsModel)

    // suspend fun clearFavList()
}