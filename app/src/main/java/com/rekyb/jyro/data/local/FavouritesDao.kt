package com.rekyb.jyro.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.OnConflictStrategy
import androidx.room.Insert
import androidx.room.Delete

@Dao
interface FavouritesDao {

    @Query("SELECT * FROM favourites_table")
    suspend fun getFavouritesList(): List<FavouritesEntity>

    @Query("SELECT EXISTS(SELECT * FROM favourites_table WHERE id = :userId)")
    suspend fun check(userId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(user: FavouritesEntity)

    @Delete
    suspend fun remove(user: FavouritesEntity)

    // @Query("DELETE FROM favourites_table")
    // suspend fun deleteAllFavEntries()
}
