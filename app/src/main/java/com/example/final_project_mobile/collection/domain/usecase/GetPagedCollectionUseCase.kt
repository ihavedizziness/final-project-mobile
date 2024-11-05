package com.example.final_project_mobile.collection.domain.usecase

import androidx.paging.PagingData
import com.example.final_project_mobile.home.domain.entity.Film
import com.example.final_project_mobile.home.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow

class GetPagedCollectionUseCase(
    private val repository: FilmRepository,
) {
    operator fun invoke(collectionType: String): Flow<PagingData<Film>> = repository.loadFilmsFlow(collectionType)
}