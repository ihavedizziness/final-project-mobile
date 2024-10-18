package com.example.final_project_mobile.di

import com.example.final_project_mobile.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainActivityModule = module {

    viewModel {
        MainViewModel(
            router = get(),
        )
    }
}
