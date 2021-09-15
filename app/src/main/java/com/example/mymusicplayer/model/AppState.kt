package com.example.mymusicplayer.model

sealed class AppState {
    data class Succes(val int: Int) : AppState()
    data class Error(val int: Int) : AppState()
    object Loading : AppState()
}
