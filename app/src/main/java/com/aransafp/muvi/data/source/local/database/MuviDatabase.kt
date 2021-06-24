package com.aransafp.muvi.data.source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aransafp.muvi.data.source.local.entity.DetailEntity
import com.aransafp.muvi.data.source.local.entity.FilmEntity

@Database(entities = [FilmEntity::class, DetailEntity::class], version = 1)
abstract class MuviDatabase : RoomDatabase() {
    abstract fun muviDao(): MuviDao

    companion object {
        @Volatile
        private var INSTANCE: MuviDatabase? = null

        fun getInstance(context: Context): MuviDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MuviDatabase::class.java,
                    "muvi db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}