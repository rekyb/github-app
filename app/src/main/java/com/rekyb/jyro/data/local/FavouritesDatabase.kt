package com.rekyb.jyro.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rekyb.jyro.domain.model.UserItemsModel

@Database(
    entities = [UserItemsModel::class],
    version = 1,
    exportSchema = false
)
abstract class FavouritesDatabase : RoomDatabase() {

    abstract fun favDao(): FavouritesDao

    companion object {
        private const val DATABASE_NAME = "favourites_db"

        fun build(context: Context): FavouritesDatabase = Room.databaseBuilder(
            context.applicationContext,
            FavouritesDatabase::class.java, DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
}