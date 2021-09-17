package com.example.mymusicplayer.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SongEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String = "",
    val songRes: Int = 0,
    val coverRes: Int = 0
)
