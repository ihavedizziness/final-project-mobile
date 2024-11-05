package com.example.final_project_mobile.home.domain.entity

import android.os.Parcelable
import com.example.final_project_mobile.home.presentation.CollectionKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilmCollection(
    val collection: CollectionKey? = null,
    val total: Int,
    val totalPages: Int,
    val items : List<Film>,
) : Parcelable

@Parcelize
data class Film(
    val kinopoiskId: Long,
    val nameRu: String,
    val nameEn: String,
    val nameOriginal: String,
    val countries: List<Country>,
    val genres: List<Genre>,
    val ratingKinopoisk: Double,
    val ratingImdb: Double,
    val year: String,
    val type: String,
    val posterUrl: String,
    val posterUrlPreview: String,
) : Parcelable

@Parcelize
data class Country(
    val country: String,
) : Parcelable

@Parcelize
data class Genre(
    val genre: String,
) : Parcelable