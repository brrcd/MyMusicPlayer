package com.example.mymusicplayer.model

import com.example.mymusicplayer.model.entity.Song

// класс для отслеживания загрузки даты из ДБ или сервера

sealed class AppState {
    data class Success(val song: Song) : AppState()
    data class Error(val int: Int) : AppState()
    object Loading : AppState()
}
