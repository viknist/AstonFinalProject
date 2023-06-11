package ru.viknist.rickandmorty.features.characters.models

data class CharacterFilter(
    val name: String,
    val status: CharacterStatus?,
    val species: String,
    val type: String,
    val gender: CharacterGender?
) : java.io.Serializable
