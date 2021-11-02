package com.rekyb.jyro.repository

import com.rekyb.jyro.data.remote.ApiService
import com.rekyb.jyro.data.remote.mapper.UserDetailsMapper
import com.rekyb.jyro.data.remote.mapper.SearchResultsMapper
import com.rekyb.jyro.data.remote.mapper.UserItemsMapper
import com.rekyb.jyro.domain.model.UserDetailsModel
import com.rekyb.jyro.domain.model.SearchResultsModel
import com.rekyb.jyro.domain.model.UserItemsModel
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val searchResponseMapper: SearchResultsMapper,
    private val getDetailsMapper: UserDetailsMapper,
    private val userItemsMapper: UserItemsMapper,
) : UserRepository {

    override suspend fun search(query: String): SearchResultsModel {
        return searchResponseMapper.mapFromEntity(apiService.search(query))
    }

    override suspend fun getDetails(userName: String): UserDetailsModel {
        return getDetailsMapper.mapFromEntity(apiService.getDetails(userName))
    }

    override suspend fun getFollowing(userName: String): List<UserItemsModel> {
        return userItemsMapper.fromDomainList(apiService.getUserFollowing(userName))
    }

    override suspend fun getFollowers(userName: String): List<UserItemsModel> {
        return userItemsMapper.fromDomainList(apiService.getUserFollowers(userName))
    }
}
