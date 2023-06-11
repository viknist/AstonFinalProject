package ru.viknist.rickandmorty.features.episodes.data.repository

import ru.viknist.rickandmorty.features.episodes.data.dto.EpisodeFilter
import ru.viknist.rickandmorty.features.episodes.data.dto.EpisodeModel
import ru.viknist.rickandmorty.features.episodes.data.dto.EpisodesResultModel
import ru.viknist.rickandmorty.features.episodes.data.service.EpisodeService
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val episodeService: EpisodeService
){

    fun getEpisodeListWeb(filter: EpisodeFilter): EpisodesResultModel{
        return episodeService.getEpisodes(
            filter.name,
            filter.episode
        )
    }

    fun getEpisodeByIdWeb(id: Int): EpisodeModel{
        return episodeService.getEpisodeById(id)
    }

    fun getEpisodesByListIdWeb(idList: List<Int>): List<EpisodeModel>{
        return episodeService.getEpisodesByListId(idList)
    }
}