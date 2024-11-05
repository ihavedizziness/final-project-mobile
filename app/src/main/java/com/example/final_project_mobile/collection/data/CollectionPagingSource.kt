package com.example.final_project_mobile.collection.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.final_project_mobile.home.domain.entity.Film
import com.example.final_project_mobile.home.domain.repository.FilmRepository

class CollectionPagingSource(
    private val repository: FilmRepository,
    private val collectionType: String,
) : PagingSource<Int, Film>() {

    override fun getRefreshKey(state: PagingState<Int, Film>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val page = params.key ?: 1

        val result = repository.loadFilmsByCollection(collectionType = collectionType, page = page)
        return result.fold(
            onSuccess = { pagedData ->
                LoadResult.Page(
                    data = pagedData.items,
                    prevKey = if (page > 1) page - 1 else null,
                    nextKey = if (page < pagedData.page.totalPages) page + 1 else null,
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }
}