package com.rekyb.jyro.di

import com.rekyb.jyro.data.remote.ApiService
import com.rekyb.jyro.data.remote.mapper.GetDetailsMapper
import com.rekyb.jyro.data.remote.mapper.SearchResponseMapper
import com.rekyb.jyro.repository.UserRepository
import com.rekyb.jyro.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        apiService: ApiService,
        searchResponseMapper: SearchResponseMapper,
        getDetailsMapper: GetDetailsMapper,
    ): UserRepository {

        return UserRepositoryImpl(
            apiService,
            searchResponseMapper,
            getDetailsMapper
        )
    }
}
