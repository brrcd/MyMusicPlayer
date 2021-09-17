package com.example.mymusicplayer.room

import androidx.room.*

@Dao
interface SongDao {

    @Query("SELECT * FROM SongEntity")
    fun all(): List<SongEntity>

    @Query ("SELECT * FROM SongEntity WHERE title = :title")
    fun selectSongByTitle(title: String): SongEntity

    @Query ("SELECT * FROM SongEntity WHERE id = :id")
    fun selectSongById(id: Long): SongEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: SongEntity)

    @Update
    fun update(entity: SongEntity)
}