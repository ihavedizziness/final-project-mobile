package com.example.final_project_mobile.home.domain.usecase

import com.example.final_project_mobile.home.domain.repository.FilmRepository

class GetFilmsByCollectionUseCase(
    private val repository: FilmRepository,
) {
    suspend operator fun invoke(collectionType: String, page: Int) =
        repository.getFilmsByCollection(collectionType, page)
}