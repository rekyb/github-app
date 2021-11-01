package com.rekyb.jyro.repository

import com.rekyb.jyro.data.local.FavouritesDao
import com.rekyb.jyro.data.remote.ApiService
import com.rekyb.jyro.data.remote.mapper.GetDetailsMapper
import com.rekyb.jyro.data.remote.mapper.SearchResponseMapper
import com.rekyb.jyro.data.remote.mapper.UserItemsMapper
import com.rekyb.jyro.domain.model.GetDetailsModel
import com.rekyb.jyro.domain.model.SearchResultsModel
import com.rekyb.jyro.domain.model.UserItemsModel
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val searchResponseMapper: SearchResponseMapper,
    private val getDetailsMapper: GetDetailsMapper,
    private val userItemsMapper: UserItemsMapper,
    private val favouritesDao: FavouritesDao,
) : UserRepository {

    override suspend fun search(query: String): SearchResultsModel {
        return searchResponseMapper.mapFromEntity(apiService.search(query))
    }

    override suspend fun getDetails(userName: String): GetDetailsModel {
        return getDetailsMapper.mapFromEntity(apiService.getDetails(userName))
    }

    override suspend fun getFollowing(userName: String): List<UserItemsModel> {
        return userItemsMapper.fromDomainList(apiService.getUserFollowing(userName))
    }

    override suspend fun getFollowers(userName: String): List<UserItemsModel> {
        return userItemsMapper.fromDomainList(apiService.getUserFollowers(userName))
    }

    override suspend fun getFavList(): List<UserItemsModel> {
        return favouritesDao.getFavouritesList()
    }

    override suspend fun getFavUserDetails(user: String): UserItemsModel {
        return favouritesDao.getUserDetail(user)
    }

    override suspend fun addUserToFavList(user: UserItemsModel) {
        return favouritesDao.addUserToFavList(user)
    }

    override suspend fun removeUserFromFavList(user: UserItemsModel) {
        return favouritesDao.deleteUserFromFavList(user)
    }
}
