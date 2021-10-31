package com.rekyb.jyro.repository

import com.rekyb.jyro.domain.model.GetDetailsModel
import com.rekyb.jyro.domain.model.SearchResultsModel
import com.rekyb.jyro.domain.model.UserItemsModel

interface UserRepository {

    suspend fun search(query: String): SearchResultsModel
    suspend fun getDetails(userName: String): GetDetailsModel
    suspend fun getFollowing(userName: String): List<UserItemsModel>
    suspend fun getFollowers(userName: String): List<UserItemsModel>
}
