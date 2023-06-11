package ru.viknist.rickandmorty.features.characters.data.dto

data class CharactersResultModel(
    val info: ResultInfoModel,
    val results: List<CharacterModel>
)
