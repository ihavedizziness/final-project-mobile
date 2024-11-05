package com.example.final_project_mobile.home.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.final_project_mobile.collection.data.CollectionPagingSource
import com.example.final_project_mobile.collection.domain.paging.Page
import com.example.final_project_mobile.collection.domain.paging.PagedData
import com.example.final_project_mobile.core.BaseRepository
import com.example.final_project_mobile.core.orZero
import com.example.final_project_mobile.home.data.mapper.ApiFilmCollectionMapper
import com.example.final_project_mobile.home.data.mapper.ApiFilmMapper
import com.example.final_project_mobile.home.domain.entity.Film
import com.example.final_project_mobile.home.domain.repository.FilmRepository
import com.example.final_project_mobile.network.FilmService
import kotlinx.coroutines.flow.Flow

class FilmRepositoryImpl(
    private val filmService: FilmService,
) : FilmRepository, BaseRepository {

    override suspend fun getFilmsByCollection(
        collectionType: String,
        page: Int,
    ) = mappedApiCall(ApiFilmCollectionMapper) {
        filmService.getMoviesByCollection(collectionType, page)
    }

    override suspend fun loadFilmsByCollection(
        collectionType: String,
        page: Int
    ): Result<PagedData<Film>> = apiCall {
        val result = filmService.getMoviesByCollection(collectionType, page)
        PagedData(
            items = result.items?.map(ApiFilmMapper::map).orEmpty(),
            page = Page(
                total = result.total.orZero(),
                totalPages = result.totalPages.orZero(),
            )
        )
    }

    override fun loadFilmsFlow(collectionType: String): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(pageSize = 30)
        ) {
            CollectionPagingSource(
                repository = this,
                collectionType = collectionType,
            )
        }.flow
    }
}