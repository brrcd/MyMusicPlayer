package com.example.mymusicplayer.model.repository

import com.example.mymusicplayer.model.entity.Song

interface Repository {

    fun getListOfSongFromDB(): List<Song>

    fun saveSongToDB(song: Song)

    fun getSongByTitle(title: String): Song

    fun getSongById(id: Long): Song
}