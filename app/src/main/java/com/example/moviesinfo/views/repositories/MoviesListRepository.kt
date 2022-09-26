package com.example.moviesinfo.views.repositories

import com.example.moviesinfo.network.ApiService
import com.example.moviesinfo.util.NetworkHelper
import com.example.moviesinfo.util.Resource
import com.example.moviesinfo.views.models.MovieDetailModel
import com.example.moviesinfo.views.models.MoviesDataModel
import com.example.moviesinfo.views.models.SearchItem
import retrofit2.Response
import javax.inject.Inject

class MoviesListRepository @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val apiService: ApiService,
) {

    suspend fun getSearchedMovie(query: String): Resource<List<SearchItem>> {
        if (networkHelper.isOnline()) {
            return searchResponseToResource(apiService.getSearchedMovies(query))
        }
        return Resource.Error("No Internet Connection. Please try again!")
    }

    private fun searchResponseToResource(response: Response<MoviesDataModel>): Resource<List<SearchItem>> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result.search)
            }
        }
        return Resource.Error(response.message().toString())
    }

    suspend fun getMovieDetails(selectedMovieId: String): Resource<MovieDetailModel> {
        if (networkHelper.isOnline()) {
            return detailsResponseToResource(apiService.getMovieDetails(selectedMovieId))
        }
        return Resource.Error("No Internet Connection. Please try again!")
    }

    private fun detailsResponseToResource(response: Response<MovieDetailModel>): Resource<MovieDetailModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message().toString())
    }
}