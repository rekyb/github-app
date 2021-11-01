package com.rekyb.jyro.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.OnConflictStrategy
import androidx.room.Insert
import androidx.room.Delete

@Dao
interface FavouritesDao {

    @Query("select * from favourites_table")
    suspend fun getFavouritesList(): List<FavouritesUsersEntity>

    @Query("SELECT * from favourites_table WHERE id = :userId")
    fun getUserDetail(userId: Int): FavouritesUsersEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserToFavList(user: FavouritesUsersEntity)

    @Delete
    suspend fun removeUserFromFavList(user: FavouritesUsersEntity)

    // @Query("DELETE FROM favourites_table")
    // suspend fun deleteAllFavEntries()
}
