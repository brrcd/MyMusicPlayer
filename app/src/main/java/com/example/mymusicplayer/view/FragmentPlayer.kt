package com.example.mymusicplayer.view

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
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

class FragmentPlayer : Fragment() {
    private val viewModel: FragmentPlayerViewModel by viewModel()
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private lateinit var mediaPlayer: MediaPlayer
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
        viewModel.getSong("Moose")
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.bensound_dubstep)

        buttonLoop.setOnClickListener {
            mediaPlayer.isLooping = !mediaPlayer.isLooping
        }
        buttonPlay.setOnClickListener {
            if (mediaPlayer.isPlaying)
                mediaPlayer.pause()
            else
                mediaPlayer.start()
        }
        buttonShuffle.setOnClickListener {
        }

        seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser)
                    mediaPlayer.seekTo(progress * 1000)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                mediaPlayer.release()
                mediaPlayer = MediaPlayer.create(requireContext(), appState.song.songRes)
            }
            is AppState.Loading -> {

            }
            is AppState.Error -> {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentPlayer()
    }
}