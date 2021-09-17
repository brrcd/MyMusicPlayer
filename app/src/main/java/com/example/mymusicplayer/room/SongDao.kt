package com.example.mymusicplayer.room

import androidx.room.*

@Dao
interface SongDao {

    @Query("SELECT * FROM SongEntity")
    fun all(): List<SongEntity>

    @Query ("SELECT * FROM SongEntity WHERE title = :title")
    fun selectSong(title: String): SongEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: SongEntity)

    @Update
    fun update(entity: SongEntity)
}