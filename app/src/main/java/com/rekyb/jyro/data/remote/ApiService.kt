package com.rekyb.jyro.data.remote

import com.rekyb.jyro.common.Constants
import com.rekyb.jyro.data.remote.response.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /**
     * https://api.github.com/search/users?q=query
     */
    @GET(Constants.GIT_SEARCH_END_POINT)
    suspend fun search(@Query("q") query: String): SearchResponseDto

}