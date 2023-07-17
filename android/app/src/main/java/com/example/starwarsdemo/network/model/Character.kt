package com.example.starwarsdemo.network.model

import com.squareup.moshi.Json

data class CharacterJson(
    val name: String,
    val height: String,
    val mass: String,
    @Json(name = "hair_color") val hairColor: String,
    @Json(name = "skin_color") val skinColor: String,
    @Json(name = "eye_color") val eyeColor: String,
    val gender: String,
    val films: List<String>,
    val url: String,
)

data class Character(
    val name: String,
    val height: String,
    val mass: String,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val gender: String,
    val filmsIds: List<String>,
    val imageUrl: String,
)

data class CharactersResponse(
    val results: List<CharacterJson>
)
