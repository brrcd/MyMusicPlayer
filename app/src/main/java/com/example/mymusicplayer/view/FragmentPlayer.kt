package com.example.mymusicplayer.view

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.mymusicplayer.R
import com.example.mymusicplayer.databinding.FragmentPlayerBinding
import com.example.mymusicplayer.model.AppState
import com.example.mymusicplayer.viewmodel.FragmentPlayerViewModel
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Runnable

class FragmentPlayer : Fragment() {
    private val viewModel: FragmentPlayerViewModel by viewModel()
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private var handler = Handler(Looper.getMainLooper())
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable: Runnable
    private lateinit var job: Job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.populateDB()
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getSongByTitle("Moose")
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.bensound_dubstep)

        job = CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            initPlayerControlButtons()
            initSeekBarListener()
        }
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                mediaPlayer = MediaPlayer.create(requireContext(), appState.song.songRes)
                musicTitle.text = appState.song.title
                cover.setImageResource(appState.song.coverRes)
                mediaPlayer.start()
            }
            is AppState.Loading -> {

            }
            is AppState.Error -> {

            }
        }
    }

    private fun FragmentPlayerBinding.initPlayerControlButtons() {
        buttonLoop.setOnClickListener {
            if (mediaPlayer.isLooping) {
                mediaPlayer.isLooping = false
                buttonLoop.setImageResource(R.drawable.ic_baseline_loop_24)
            } else {
                mediaPlayer.isLooping = true
                buttonLoop.setImageResource(R.drawable.ic_baseline_cancel_24)
            }
        }
        buttonPlay.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                buttonPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            } else {
                mediaPlayer.start()
                buttonPlay.setImageResource(R.drawable.ic_baseline_pause_24)
            }
        }
        buttonShuffle.setOnClickListener {
            mediaPlayer.release()
            viewModel.getRandomSong()
        }
    }

    private fun FragmentPlayerBinding.initSeekBarListener() {
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser)
                    mediaPlayer.seekTo(progress * 1000)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        seekbar.max = mediaPlayer.duration / 1000

        runnable = Runnable {
            seekbar.progress = mediaPlayer.currentPosition / 1000
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentPlayer()
    }
}