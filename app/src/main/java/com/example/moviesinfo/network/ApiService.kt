package com.example.moviesinfo.network

import com.example.moviesinfo.BuildConfig
import com.example.moviesinfo.views.models.MovieDetailModel
import com.example.moviesinfo.views.models.MoviesDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/")
    suspend fun getSearchedMovies(
        @Query("s") query: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<MoviesDataModel>

    @GET("/")
    suspend fun getMovieDetails(
        @Query("i") imdbID: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<MovieDetailModel>

}