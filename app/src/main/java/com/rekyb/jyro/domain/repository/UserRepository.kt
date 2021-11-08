package com.rekyb.jyro.domain.repository

import com.rekyb.jyro.domain.model.SearchResultsModel
import com.rekyb.jyro.domain.model.UserDetailsModel
import com.rekyb.jyro.domain.model.UserItemsModel

interface UserRepository {

    suspend fun search(query: String): SearchResultsModel
    suspend fun getDetails(userName: String): UserDetailsModel
    suspend fun getFollowing(userName: String): List<UserItemsModel>
    suspend fun getFollowers(userName: String): List<UserItemsModel>
}
