package ru.viknist.rickandmorty.features.characters.data.dto

import ru.viknist.rickandmorty.features.characters.util.CharacterGender
import ru.viknist.rickandmorty.features.characters.util.CharacterStatus

data class CharacterFilter(
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String,
    val gender: CharacterGender
)
