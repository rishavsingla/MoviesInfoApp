package com.example.moviesinfo.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesinfo.util.Resource
import com.example.moviesinfo.views.models.MovieDetailModel
import com.example.moviesinfo.views.repositories.MoviesListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val moviesListRepository: MoviesListRepository) :
    ViewModel() {

    private val movieDetailsData = MutableLiveData<Resource<MovieDetailModel>>()
    fun getMovieDetails(): LiveData<Resource<MovieDetailModel>> = movieDetailsData

    fun getMovieDetails(selectedMovieId: String) = viewModelScope.launch(Dispatchers.IO) {
        movieDetailsData.postValue(Resource.Loading())
        try {
            val apiResult = moviesListRepository.getMovieDetails(selectedMovieId)
            movieDetailsData.postValue(apiResult)
        } catch (e: Exception) {
            movieDetailsData.postValue(Resource.Error(e.message.toString()))
        }

    }


}