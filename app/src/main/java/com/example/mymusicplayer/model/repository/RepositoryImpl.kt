package com.example.mymusicplayer.model.repository

import com.example.mymusicplayer.model.entity.Song
import com.example.mymusicplayer.room.SongDatabase
import com.example.mymusicplayer.room.SongEntity

class RepositoryImpl: Repository {

    override fun getListOfSongFromDB(): List<Song> =
        convertEntityListToSongList(SongDatabase.db.songDao().all())

    override fun saveSongToDB(song: Song) =
        SongDatabase.db.songDao().insert(convertSongToEntity(song))


    override fun getSong(title: String): Song =
        convertEntityToSong(SongDatabase.db.songDao().selectSong(title))


    private fun convertEntityToSong(entity: SongEntity) =
        Song(
            entity.title,
            entity.songRes,
            entity.coverRes
        )

    private fun convertSongToEntity(song: Song) =
        SongEntity(
            0,
            song.title,
            song.songRes,
            song.coverRes
        )

    private fun convertEntityListToSongList(entityList: List<SongEntity>) =
        entityList.map {
            convertEntityToSong(it)
        }
}