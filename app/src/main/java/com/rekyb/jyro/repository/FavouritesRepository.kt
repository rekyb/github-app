package com.rekyb.jyro.repository

import com.rekyb.jyro.domain.model.UserDetailsModel
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {

    fun getFavouritesList(): Flow<List<UserDetailsModel>>
    suspend fun checkUserOnFavList(userName: String): Boolean
    suspend fun addUserToFavList(user: UserDetailsModel)
    suspend fun removeUserFromFavList(user: UserDetailsModel)
    suspend fun clearFavList()
}
