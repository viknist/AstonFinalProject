package ru.viknist.rickandmorty.features.characters.data.dto

import ru.viknist.rickandmorty.baseapp.data.dto.ResultInfoResponse

data class CharactersResultResponse(
    val info: ResultInfoResponse,
    val results: List<CharacterResponse>
)
