package com.rekyb.jyro.di

import com.rekyb.jyro.data.local.FavouritesDao
import com.rekyb.jyro.data.remote.ApiService
import com.rekyb.jyro.data.remote.mapper.SearchResultsMapper
import com.rekyb.jyro.data.remote.mapper.UserDetailsMapper
import com.rekyb.jyro.data.remote.mapper.UserItemsMapper
import com.rekyb.jyro.repository.FavouritesRepository
import com.rekyb.jyro.repository.FavouritesRepositoryImpl
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
        searchResponseMapper: SearchResultsMapper,
        getDetailsMapper: UserDetailsMapper,
        userItemsMapper: UserItemsMapper,
    ): UserRepository {

        return UserRepositoryImpl(
            apiService,
            searchResponseMapper,
            getDetailsMapper,
            userItemsMapper,
        )
    }

    @Provides
    @Singleton
    fun provideFavouritesRepository(favouritesDao: FavouritesDao): FavouritesRepository {
        return FavouritesRepositoryImpl(favouritesDao)
    }
}
