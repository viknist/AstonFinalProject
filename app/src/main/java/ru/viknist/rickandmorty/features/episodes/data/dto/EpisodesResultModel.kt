package ru.viknist.rickandmorty.features.episodes.data.dto

import ru.viknist.rickandmorty.features.characters.data.dto.ResultInfoModel

data class EpisodesResultModel(
    val info: ResultInfoModel,
    val results: List<EpisodeModel>
)
