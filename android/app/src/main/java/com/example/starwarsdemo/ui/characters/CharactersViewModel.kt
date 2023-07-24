package com.example.starwarsdemo.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsdemo.network.ApiStatus
import com.example.starwarsdemo.network.model.Character
import com.example.starwarsdemo.network.model.Movie
import com.example.starwarsdemo.network.StarWarsApi
import com.example.starwarsdemo.network.model.DataTransformer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class CharactersViewModel: ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _people = MutableLiveData<List<Character>>()
    val people: LiveData<List<Character>> = _people

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    val maxIndex = 9
    private val _currentIndex = MutableLiveData<Int>(3)
    val currentIndex: LiveData<Int> = _currentIndex

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val mergedCharactersResponse = listOf(
                    async { StarWarsApi.retrofitService.getCharacters(1) },
                    async { StarWarsApi.retrofitService.getCharacters(2) },
                    async { StarWarsApi.retrofitService.getCharacters(3) },
                )

                val flattenResults = mergedCharactersResponse.awaitAll().flatMap { it.results }
                withContext(Dispatchers.Main) {
                    _people.value = DataTransformer.charactersFromCharactersJson(flattenResults)
                }
                _status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                _people.value = emptyList()
                _status.value = ApiStatus.ERROR
            }
        }
    }

    fun fetchNextPageData() {
        if (_status.value == ApiStatus.LOADING) return

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _currentIndex.value?.let {
                    val newPage = it.plus(1)
                    val response = StarWarsApi.retrofitService.getCharacters(newPage)

                    if (response.results.isNotEmpty()) {
                        withContext(Dispatchers.Main) {
                            _people.apply {
                                value = value?.plus(DataTransformer.charactersFromCharactersJson(response.results))
                                _currentIndex.value = newPage
                            }
                        }
                    }
                }

                _status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
    }

}