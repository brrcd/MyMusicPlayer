package com.example.mymusicplayer.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mymusicplayer.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, FragmentPlayer.newInstance())
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }
}