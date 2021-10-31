package com.rekyb.jyro.repository

import com.rekyb.jyro.data.remote.ApiService
import com.rekyb.jyro.data.remote.mapper.GetDetailsMapper
import com.rekyb.jyro.data.remote.mapper.SearchResponseMapper
import com.rekyb.jyro.domain.model.GetDetailsModel
import com.rekyb.jyro.domain.model.SearchModel
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val searchResponseMapper: SearchResponseMapper,
    private val getDetailsMapper: GetDetailsMapper
) : UserRepository {

    override suspend fun search(query: String): SearchModel {
        return searchResponseMapper.mapFromEntity(apiService.search(query))
    }

    override suspend fun getDetails(userName: String): GetDetailsModel {
        return getDetailsMapper.mapFromEntity(apiService.getDetails(userName))
    }
}
