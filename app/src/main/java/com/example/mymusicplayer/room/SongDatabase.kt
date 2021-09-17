package com.example.mymusicplayer.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymusicplayer.App

@Database(
    entities = [SongEntity::class],
    version = 1,
    exportSchema = false
)

abstract class SongDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao

    companion object {
        private const val DB_NAME = "songs_database.db"
        val db: SongDatabase by lazy {
            Room.databaseBuilder(
                App.appContext,
                SongDatabase::class.java,
                DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}