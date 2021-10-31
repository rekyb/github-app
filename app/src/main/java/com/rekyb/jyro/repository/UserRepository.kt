package com.rekyb.jyro.repository

import com.rekyb.jyro.domain.model.GetDetailsModel
import com.rekyb.jyro.domain.model.SearchModel

interface UserRepository {

    suspend fun search(query: String): SearchModel
    suspend fun getDetails(userName: String): GetDetailsModel
}
