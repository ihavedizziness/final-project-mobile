package com.example.final_project_mobile.home.data.mapper

import com.example.final_project_mobile.core.BaseMapper
import com.example.final_project_mobile.core.orZero
import com.example.final_project_mobile.home.data.entity.ApiFilm
import com.example.final_project_mobile.home.domain.entity.Country
import com.example.final_project_mobile.home.domain.entity.Film
import com.example.final_project_mobile.home.domain.entity.Genre

object ApiFilmMapper : BaseMapper<ApiFilm?, Film> {

    override fun map(
        source: ApiFilm?,
    ): Film = Film(
        kinopoiskId = source?.kinopoiskId.orZero(),
        nameRu = source?.nameRu.orEmpty(),
        nameEn = source?.nameEn.orEmpty(),
        nameOriginal = source?.nameOriginal.orEmpty(),
        countries = source?.countries?.map { Country(it?.country.orEmpty()) }.orEmpty(),
        genres = source?.genres?.map { Genre(it?.genre.orEmpty()) }.orEmpty(),
        ratingKinopoisk = source?.ratingKinopoisk.orZero(),
        ratingImdb = source?.ratingImdb.orZero(),
        year = source?.year.orEmpty(),
        type = source?.type.orEmpty(),
        posterUrl = source?.posterUrl.orEmpty(),
        posterUrlPreview = source?.posterUrlPreview.orEmpty(),
    )
}