package com.example.moviesinfo.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesinfo.util.Resource
import com.example.moviesinfo.views.models.SearchItem
import com.example.moviesinfo.views.repositories.MoviesListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(private val moviesListRepository: MoviesListRepository) :
    ViewModel() {

    private val moviesListMutableLiveData = MutableLiveData<Resource<List<SearchItem>>>()
    fun getMoviesList(): LiveData<Resource<List<SearchItem>>> = moviesListMutableLiveData

    fun getSearchedMovie(query: String) = viewModelScope.launch(Dispatchers.IO) {
        moviesListMutableLiveData.postValue(Resource.Loading())
        try {
            val apiResult = moviesListRepository.getSearchedMovie(query)
            moviesListMutableLiveData.postValue(apiResult)
        } catch (e: Exception) {
            moviesListMutableLiveData.postValue(Resource.Error(e.message.toString()))
        }
    }


}