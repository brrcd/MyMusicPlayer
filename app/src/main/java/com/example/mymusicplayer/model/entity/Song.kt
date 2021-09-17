package com.example.mymusicplayer.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val title: String = "",
    val songRes: Int = 0,
    val coverRes: Int = 0
) : Parcelable