package com.rekyb.jyro.di

import android.content.Context
import com.rekyb.jyro.data.local.FavouritesDao
import com.rekyb.jyro.data.local.FavouritesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideFavouritesDatabase(@ApplicationContext context: Context): FavouritesDatabase {
        return FavouritesDatabase.build(context)
    }

    @Singleton
    @Provides
    fun provideFavouritesDao(database: FavouritesDatabase): FavouritesDao {
        return database.favDao()
    }
}