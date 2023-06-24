package ru.viknist.rickandmorty.features.episodes.data.dto

import ru.viknist.rickandmorty.baseapp.data.dto.ResultInfoResponse

data class EpisodesResultResponse(
    val info: ResultInfoResponse,
    val results: List<EpisodeResponse>
)
