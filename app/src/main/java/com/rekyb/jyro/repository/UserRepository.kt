package com.rekyb.jyro.repository

import com.rekyb.jyro.domain.model.SearchResponse

interface UserRepository {

    suspend fun search(query: String): SearchResponse

}