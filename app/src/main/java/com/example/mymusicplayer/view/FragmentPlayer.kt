package com.example.mymusicplayer.view

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mymusicplayer.R
import com.example.mymusicplayer.databinding.FragmentPlayerBinding
import com.example.mymusicplayer.viewmodel.FragmentPlayerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentPlayer : Fragment() {
    private val viewModel: FragmentPlayerViewModel by viewModel()
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        var mediaPlayer: MediaPlayer = MediaPlayer.create(requireContext(), R.raw.bensound_dubstep)
        buttonLoop.setOnClickListener {
            mediaPlayer.isLooping = !mediaPlayer.isLooping
        }
        buttonPlay.setOnClickListener {
            mediaPlayer.start()
        }
        buttonShuffle.setOnClickListener {
        }
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