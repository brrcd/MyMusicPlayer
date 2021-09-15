package com.example.mymusicplayer.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mymusicplayer.R
import com.example.mymusicplayer.model.entity.Song
import com.example.mymusicplayer.model.repository.Repository
import kotlin.random.Random

class FragmentPlayerViewModel(private val repository: Repository) : ViewModel() {
    private val songs = listOf(
        Song("Dubstep", R.raw.bensound_dubstep, R.drawable.brick_wall),
        Song("Epic", R.raw.bensound_epic, R.drawable.nebula),
        Song("Moose", R.raw.bensound_moose, R.drawable.tape)
    )

    fun getRandomSong() {

    }

}