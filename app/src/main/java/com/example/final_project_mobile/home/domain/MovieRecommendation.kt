package com.example.final_project_mobile.home.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieRecommendation(
    val title: String,
    val recommendationList: List<Movie>
) : Parcelable {

    companion object {
        val dummyMovies = List(8) { Movie.dummy }
    }
}

@Parcelize
data class Movie(
    val image: String,
    val title: String,
    val genre: String,
    val rating: Double,
) : Parcelable {

    companion object {
        val dummy = Movie(
            image = "",
            title = "Близкие",
            genre = "Драма",
            rating = 7.8,
        )
    }
}