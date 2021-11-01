package com.rekyb.jyro.data.local


import androidx.room.Dao
import androidx.room.Query
import androidx.room.OnConflictStrategy
import androidx.room.Insert
import androidx.room.Delete
import com.rekyb.jyro.domain.model.UserItemsModel

@Dao
interface FavouritesDao {

    @Query("select * from favourites_table")
    suspend fun getFavouritesList(): List<UserItemsModel>

    @Query("SELECT * from favourites_table WHERE userName = :username")
    fun getUserDetail(username: String): UserItemsModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserToFavList(user: UserItemsModel)

    @Delete
    suspend fun deleteUserFromFavList(user: UserItemsModel)

    @Query("DELETE FROM favourites_table")
    suspend fun deleteAllFavEntries()
}
