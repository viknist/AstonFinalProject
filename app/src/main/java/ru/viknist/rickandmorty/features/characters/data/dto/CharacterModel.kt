package ru.viknist.rickandmorty.features.characters.data.dto

data class CharacterModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: CharactersLocationModel,
    val location: CharactersLocationModel,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)
