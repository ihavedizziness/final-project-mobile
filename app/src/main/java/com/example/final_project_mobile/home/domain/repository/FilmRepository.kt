package com.example.final_project_mobile.home.domain.repository

import androidx.paging.PagingData
import com.example.final_project_mobile.collection.domain.paging.PagedData
import com.example.final_project_mobile.home.domain.entity.Film
import com.example.final_project_mobile.home.domain.entity.FilmCollection
import kotlinx.coroutines.flow.Flow

interface FilmRepository {
    suspend fun getFilmsByCollection(collectionType: String, page: Int): Result<FilmCollection>
    suspend fun loadFilmsByCollection(collectionType: String, page: Int): Result<PagedData<Film>>
    fun loadFilmsFlow(collectionType: String): Flow<PagingData<Film>>
}