package com.example.mymusicplayer.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.mymusicplayer.R
import com.example.mymusicplayer.databinding.FragmentPlayerBinding
import com.example.mymusicplayer.model.AppState
import com.example.mymusicplayer.service.MediaPlayerNotification
import com.example.mymusicplayer.viewmodel.FragmentPlayerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Runnable

class FragmentPlayer : Fragment(), Control {
    private val viewModel: FragmentPlayerViewModel by viewModel()
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaPlayerNotification: MediaPlayerNotification

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mediaPlayerNotification = MediaPlayerNotification()
        mediaPlayer = MediaPlayer()

        activity?.registerReceiver(broadcastReceiver, IntentFilter("action_name"))

        viewModel.populateDB()
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getSongByTitle("Moose")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                mediaPlayer = MediaPlayer.create(requireContext(), appState.song.songRes)
                mediaPlayerNotification.createNotification(requireActivity(), appState.song)
                musicTitle.text = appState.song.title
                cover.setImageResource(appState.song.coverRes)
                initPlayerControlButtons()
            }
            is AppState.Loading -> {

            }
            is AppState.Error -> {

            }
        }
    }

    private var broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.extras?.getString("action_name")) {
                MediaPlayerNotification.ACTION_PLAY -> {
                    onPressPlay()
                }
                MediaPlayerNotification.ACTION_LOOP -> {
                    onPressLoop()
                }
                MediaPlayerNotification.ACTION_SHUFFLE -> {
                    onPressShuffle()
                }
            }
        }
    }

    private fun initPlayerControlButtons() = with(binding) {
        buttonLoop.setOnClickListener {
            onPressLoop()
        }
        buttonPlay.setOnClickListener {
            onPressPlay()
        }
        buttonShuffle.setOnClickListener {
            onPressShuffle()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        activity?.unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentPlayer()
    }

    override fun onPressLoop() {
        if (mediaPlayer.isLooping) {
            mediaPlayer.isLooping = false
            binding.buttonLoop.setImageResource(R.drawable.ic_baseline_loop_24)
        } else {
            mediaPlayer.isLooping = true
            binding.buttonLoop.setImageResource(R.drawable.ic_baseline_cancel_24)
        }
    }

    override fun onPressPlay() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            binding.buttonPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        } else {
            mediaPlayer.start()
            binding.buttonPlay.setImageResource(R.drawable.ic_baseline_pause_24)
        }
    }

    // при свернутом приложении если нажать после этой кнопки на кнопку Play приложение крашится
    // плеер не получает песню из viewModel
    override fun onPressShuffle() {
        mediaPlayer.release()
        viewModel.getRandomSong()
    }
}