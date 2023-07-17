package com.example.starwarsdemo.network

import com.example.starwarsdemo.network.model.CharactersResponse
import com.example.starwarsdemo.network.model.MoviesResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://swapi.py4e.com/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface StarWarsApiService {
    @GET("films")
    suspend fun getMovies(): MoviesResponse

    @GET("people")
    suspend fun getCharacters(@Query("page") page: Int): CharactersResponse
}

object StarWarsApi {
    val retrofitService : StarWarsApiService by lazy {
        retrofit.create(StarWarsApiService::class.java)
    }
}