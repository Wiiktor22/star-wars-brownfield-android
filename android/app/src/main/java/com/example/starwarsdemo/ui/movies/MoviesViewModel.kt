package com.example.starwarsdemo.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsdemo.network.ApiStatus
import com.example.starwarsdemo.network.model.Movie
import com.example.starwarsdemo.network.StarWarsApi
import com.example.starwarsdemo.network.model.DataTransformer
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val result = StarWarsApi.retrofitService.getMovies()
                _movies.value = DataTransformer.moviesFromMoviesJson(result.results)
                _status.value = ApiStatus.SUCCESS
            } catch (e: java.lang.Exception) {
                _status.value = ApiStatus.ERROR
                _movies.value = emptyList()
            }
        }
    }
}