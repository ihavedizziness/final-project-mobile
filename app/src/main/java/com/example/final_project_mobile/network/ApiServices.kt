package com.example.final_project_mobile.network

import com.example.final_project_mobile.home.data.entity.ApiFilmCollection
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmService {

    @GET("v2.2/films/collections")
    suspend fun getMoviesByCollection(
        @Query("type") collectionType: String,
        @Query("page") page: Int,
    ): ApiFilmCollection
}