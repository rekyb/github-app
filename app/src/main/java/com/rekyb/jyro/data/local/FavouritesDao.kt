package com.rekyb.jyro.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {

    @Query("SELECT * FROM favourites_table")
    fun getFavouritesList(): Flow<List<FavouritesEntity>>

    @Query("SELECT EXISTS(SELECT * FROM favourites_table WHERE login = :userName)")
    suspend fun check(userName: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(user: FavouritesEntity)

    @Delete
    suspend fun remove(user: FavouritesEntity)

    @Query("DELETE FROM favourites_table")
    suspend fun deleteAll()
}
