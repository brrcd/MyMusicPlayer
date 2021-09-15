package com.example.mymusicplayer.di

import com.example.mymusicplayer.model.repository.Repository
import com.example.mymusicplayer.model.repository.RepositoryImpl
import com.example.mymusicplayer.viewmodel.FragmentPlayerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Repository> { RepositoryImpl() }

    //viewModels
    viewModel { FragmentPlayerViewModel(get()) }
}