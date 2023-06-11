package ru.viknist.rickandmorty.features.episodes.domain.entity

import ru.viknist.rickandmorty.baseapp.domain.entity.PageInfo

data class EpisodeData(
    val info: PageInfo,
    val episodeList: List<EpisodeEntity>
)