package com.rekyb.jyro.repository

import com.rekyb.jyro.data.remote.ApiService
import com.rekyb.jyro.data.remote.mapper.SearchResponseMapper
import com.rekyb.jyro.domain.model.SearchResponse
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: SearchResponseMapper,
) : UserRepository {

    override suspend fun search(query: String): SearchResponse {
        return mapper.mapFromEntity(apiService.search(query))
    }
}
