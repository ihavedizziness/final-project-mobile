package com.example.final_project_mobile.home.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiFilmCollection(
    val total: Int?,
    val totalPages: Int?,
    val items: List<ApiFilm?>?,
)

@JsonClass(generateAdapter = true)
data class ApiFilm(
    val kinopoiskId: Long?,
    val nameRu: String?,
    val nameEn: String?,
    val nameOriginal: String?,
    val countries: List<ApiCountry?>?,
    val genres: List<ApiGenre?>?,
    val ratingKinopoisk: Double?,
    @Json(name = "ratingImbd") val ratingImdb: Double?,
    val year: String?,
    val type: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
)

@JsonClass(generateAdapter = true)
data class ApiCountry(
    val country: String?,
)

@JsonClass(generateAdapter = true)
data class ApiGenre(
    val genre: String?,
)
