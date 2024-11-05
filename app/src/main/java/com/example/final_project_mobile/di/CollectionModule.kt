package com.example.final_project_mobile.di

import com.example.final_project_mobile.collection.domain.usecase.GetPagedCollectionUseCase
import com.example.final_project_mobile.collection.presentation.CollectionFragment
import com.example.final_project_mobile.collection.presentation.CollectionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val collectionModule = module {

    factory {
        GetPagedCollectionUseCase(repository = get())
    }

    viewModel { (args: CollectionFragment.Args) ->
        CollectionViewModel(
            router = get(),
            args = args,
            getPagedCollectionUseCase = get(),
        )
    }
}