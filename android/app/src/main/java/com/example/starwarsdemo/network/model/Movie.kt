package com.example.starwarsdemo.network.model

import com.squareup.moshi.Json
import java.io.Serializable

data class MovieJson(
    val title: String,
    val director: String,
    val producer: String,
    @Json(name = "release_date") val releaseDate: String,
    val url: String
)

data class Movie(
    val title: String,
    val director: String,
    val producer: String,
    val releaseDate: String,
    val id: String?,
    val imageUrl: String?,
) : Serializable

data class MoviesResponse(
    val results: List<MovieJson>
)