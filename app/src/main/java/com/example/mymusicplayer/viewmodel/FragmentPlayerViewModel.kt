package com.example.mymusicplayer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymusicplayer.R
import com.example.mymusicplayer.model.AppState
import com.example.mymusicplayer.model.entity.Song
import com.example.mymusicplayer.model.repository.Repository
import kotlinx.coroutines.*
import kotlin.random.Random

class FragmentPlayerViewModel(private val repository: Repository) : ViewModel() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private var job: Job? = null

    //наполняем ДБ
    fun populateDB() {
        job = CoroutineScope(Dispatchers.Default).launch {
            repository.saveSongToDB(Song("Dubstep", R.raw.bensound_dubstep, R.drawable.brick_wall));
            repository.saveSongToDB(Song("Epic", R.raw.bensound_epic, R.drawable.nebula));
            repository.saveSongToDB(Song("Moose", R.raw.bensound_moose, R.drawable.tape))
        }
    }

    fun getLiveData() = liveDataToObserve

    fun getSongByTitle(title: String) {
        liveDataToObserve.value = AppState.Loading
        job = CoroutineScope(Dispatchers.Default).launch {
            val data = repository.getSongByTitle(title)
            liveDataToObserve.postValue(AppState.Success(data))
        }
    }

    fun getRandomSong() {
        liveDataToObserve.value = AppState.Loading
        job = CoroutineScope(Dispatchers.Default).launch {
            val data = repository.getSongById(Random.nextLong(1, 4))
            liveDataToObserve.postValue(AppState.Success(data))
        }
    }
}