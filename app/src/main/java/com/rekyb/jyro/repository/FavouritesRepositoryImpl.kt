package com.rekyb.jyro.repository

import com.rekyb.jyro.data.local.FavouritesDao
import com.rekyb.jyro.data.local.FavouritesEntity
import com.rekyb.jyro.domain.model.UserDetailsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouritesRepositoryImpl @Inject constructor(
    private val favouritesDao: FavouritesDao,
) : FavouritesRepository {

    override fun getFavouritesList(): Flow<List<UserDetailsModel>> {
        return favouritesDao.getFavouritesList()
            .map { data ->
                data.map {
                    it.toDetailModel()
                }
            }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
    }

    override suspend fun checkUserOnFavList(userName: String): Boolean {
        return favouritesDao.check(userName)
    }

    override suspend fun addUserToFavList(user: UserDetailsModel) {
        return favouritesDao.add(FavouritesEntity.toEntityFrom(user))
    }

    override suspend fun removeUserFromFavList(user: UserDetailsModel) {
        return favouritesDao.remove(FavouritesEntity.toEntityFrom(user))
    }

    override suspend fun clearFavList() {
        return favouritesDao.deleteAll()
    }
}