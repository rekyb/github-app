package com.rekyb.jyro.utils

import com.rekyb.jyro.common.Constants
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.request()
        val response = originalResponse
            .newBuilder()
            .addHeader("Authorization", Constants.GITHUB_API_KEY)
            .url(originalResponse.url)
            .build()

        return chain.proceed(response)
    }
}
