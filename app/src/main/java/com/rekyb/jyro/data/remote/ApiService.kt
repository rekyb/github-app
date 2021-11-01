package com.rekyb.jyro.data.remote

import com.rekyb.jyro.data.remote.response.UserDetailsDto
import com.rekyb.jyro.data.remote.response.SearchResultsDto
import com.rekyb.jyro.data.remote.response.UserItemsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /**
     * https://api.github.com/search/users?q=query
     */
    @GET("search/users")
    suspend fun search(@Query("q") query: String): SearchResultsDto

    /**
     * https://api.github.com/users/{username}
     */
    @GET("users/{username}")
    suspend fun getDetails(@Path("username") username: String)
            : UserDetailsDto

    /**
     * https://api.github.com/users/username/followers
     */
    @GET("users/{username}/followers")
    suspend fun getUserFollowers(@Path("username") username: String)
            : List<UserItemsDto>

    /**
     * https://api.github.com/users/username/following
     */
    @GET("users/{username}/following")
    suspend fun getUserFollowing(@Path("username") username: String)
            : List<UserItemsDto>
}
