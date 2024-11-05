package com.example.final_project_mobile.home.data.mapper

import com.example.final_project_mobile.core.BaseMapper
import com.example.final_project_mobile.core.orZero
import com.example.final_project_mobile.home.data.entity.ApiFilmCollection
import com.example.final_project_mobile.home.domain.entity.FilmCollection

object ApiFilmCollectionMapper : BaseMapper<ApiFilmCollection, FilmCollection> {

    override fun map(
        source: ApiFilmCollection,
    ): FilmCollection = FilmCollection(
        total = source.total.orZero(),
        totalPages = source.totalPages.orZero(),
        items = source.items?.map { ApiFilmMapper.map(it) }.orEmpty(),
    )
}