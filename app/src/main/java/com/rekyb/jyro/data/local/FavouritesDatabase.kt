package com.rekyb.jyro.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavouritesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FavouritesDatabase : RoomDatabase() {

    abstract fun favouritesDao(): FavouritesDao

    companion object {
        @Volatile
        private var INSTANCE: FavouritesDatabase? = null
        private const val DATABASE_NAME = "favourites_db"

        fun build(context: Context): FavouritesDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    FavouritesDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance

                return instance
            }
        }
    }
}
