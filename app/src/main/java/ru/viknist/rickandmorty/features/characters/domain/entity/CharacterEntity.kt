package ru.viknist.rickandmorty.features.characters.domain.entity

import ru.viknist.rickandmorty.features.characters.models.CharacterGender
import ru.viknist.rickandmorty.features.characters.models.CharacterStatus

data class CharacterEntity(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String,
    val gender: CharacterGender,
    val origin: CharacterLocation,
    val location: CharacterLocation,
    val image: String,
    val episode: List<Int>,
    val url: String,
    val created: String
)
