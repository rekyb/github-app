package com.rekyb.jyro.di

import com.rekyb.jyro.common.Constants
import com.rekyb.jyro.data.remote.ApiService
import com.rekyb.jyro.data.remote.mapper.SearchResultsMapper
import com.rekyb.jyro.data.remote.mapper.UserDetailsMapper
import com.rekyb.jyro.data.remote.mapper.UserItemsMapper
import com.rekyb.jyro.utils.RequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .addInterceptor(loggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.GIT_BASE_API_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchResponseMapper(): SearchResultsMapper = SearchResultsMapper()

    @Provides
    @Singleton
    fun provideGetDetailsResponseMapper(): UserDetailsMapper = UserDetailsMapper()

    @Provides
    @Singleton
    fun provideUserItemsMapper(): UserItemsMapper = UserItemsMapper()
}
