package com.example.final_project_mobile.di

import com.example.final_project_mobile.home.data.FilmRepositoryImpl
import com.example.final_project_mobile.home.domain.repository.FilmRepository
import com.example.final_project_mobile.home.domain.usecase.GetFilmsByCollectionUseCase
import com.example.final_project_mobile.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    single<FilmRepository> {
        FilmRepositoryImpl(filmService = get())
    }

    factory {
        GetFilmsByCollectionUseCase(repository = get())
    }

    viewModel {
        HomeViewModel(
            router = get(),
            getMoviesByCollectionUseCase = get(),
        )
    }
}