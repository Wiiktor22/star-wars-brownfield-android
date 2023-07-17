package com.example.starwarsdemo.network.model

object DataTransformer {
    private fun getIdFromTheUrl (url: String): String? {
        if (!url.contains("/")) {
            return null
        }
        val startIndex = url.lastIndexOf("/", url.length - 2) + 1
        return url.subSequence(startIndex, url.length - 1).toString()
    }

    private fun createImageUrl (dataClass: Class<*>, id: String?): String {
        id?.let {
            return when (dataClass) {
                Movie::class.java -> "https://starwars-visualguide.com/assets/img/films/${it}.jpg"
                Character::class.java -> "https://starwars-visualguide.com/assets/img/characters/${it}.jpg"
                else -> ""
            }
            return "https://starwars-visualguide.com/assets/img/films/${it}.jpg"
        }
        return ""
    }

    private fun getFilmsIds (filmsUrls: List<String>): List<String> {
        return filmsUrls.mapNotNull { getIdFromTheUrl(it) }
    }

    fun moviesFromMoviesJson(moviesJson: List<MovieJson>): List<Movie> {
        return moviesJson.map {
            with (it) {
                val id = getIdFromTheUrl(it.url)
                Movie(
                    title = title,
                    director = director,
                    producer = producer,
                    releaseDate = releaseDate,
                    id = id,
                    imageUrl = createImageUrl(Movie::class.java, id),
                )
            }
        }
    }

    fun charactersFromCharactersJson(charactersJson: List<CharacterJson>): List<Character> {
        return charactersJson.map {
            with(it) {
                val id = getIdFromTheUrl(url)
                Character(
                    name = name,
                    height = height,
                    mass = mass,
                    hairColor = hairColor,
                    skinColor = skinColor,
                    eyeColor = eyeColor,
                    gender = gender,
                    filmsIds = getFilmsIds(films),
                    imageUrl = createImageUrl(Character::class.java, id)
                )
            }
        }
    }
}