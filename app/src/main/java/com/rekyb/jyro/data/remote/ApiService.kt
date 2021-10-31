package com.rekyb.jyro.data.remote

import com.rekyb.jyro.data.remote.response.GetDetailsResponseDto
import com.rekyb.jyro.data.remote.response.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /**
     * https://api.github.com/search/users?q=query
     */
    @GET("search/users")
    suspend fun search(@Query("q") query: String): SearchResponseDto

    /**
     * https://api.github.com/users/{username}
     */
    @GET("users/{username}")
    suspend fun getDetails(@Path("username") username: String)
            : GetDetailsResponseDto
}
