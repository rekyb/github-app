package com.rekyb.jyro.repository

import com.rekyb.jyro.data.local.FavouritesDao
import com.rekyb.jyro.data.local.FavouritesEntity
import com.rekyb.jyro.domain.model.UserDetailsModel
import javax.inject.Inject

class FavouritesRepositoryImpl @Inject constructor(
    private val favouritesDao: FavouritesDao,
) : FavouritesRepository {

    override suspend fun getFavouritesList(): List<UserDetailsModel> {
        return favouritesDao.getFavouritesList().map { it.toModel() }
    }

    override suspend fun checkUserOnFavList(userId: Int): Boolean {
        return favouritesDao.check(userId)
    }

    override suspend fun addUserToFavList(user: UserDetailsModel) {
        return favouritesDao.add(FavouritesEntity.toEntityFrom(user))
    }

    override suspend fun removeUserFromFavList(user: UserDetailsModel) {
        return favouritesDao.remove(FavouritesEntity.toEntityFrom(user))
    }
}