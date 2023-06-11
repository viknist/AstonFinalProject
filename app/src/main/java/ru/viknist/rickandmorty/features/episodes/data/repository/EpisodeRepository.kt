package ru.viknist.rickandmorty.features.episodes.data.repository

import ru.viknist.rickandmorty.features.episodes.data.database.dao.EpisodeDao
import ru.viknist.rickandmorty.features.episodes.data.database.entity.EpisodeEntityDb
import ru.viknist.rickandmorty.features.episodes.models.EpisodeFilter
import ru.viknist.rickandmorty.features.episodes.data.dto.EpisodeResponse
import ru.viknist.rickandmorty.features.episodes.data.dto.EpisodesResultResponse
import ru.viknist.rickandmorty.features.episodes.data.service.EpisodeService
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val episodeService: EpisodeService,
    private val episodeDao: EpisodeDao
) {

    suspend fun getEpisodeListWeb(filter: EpisodeFilter, page: Int): EpisodesResultResponse {
        return episodeService.getEpisodes(
            page,
            filter.name,
            filter.episode
        )
    }

    suspend fun getEpisodeByIdWeb(id: Int): EpisodeResponse {
        return episodeService.getEpisodeById(id)
    }

    suspend fun getEpisodeByIdDb(id: Int): EpisodeEntityDb {
        return episodeDao.getSingleEpisode(id)
    }

    suspend fun getEpisodesByListIdWeb(idList: List<Int>): List<EpisodeResponse> {
        return episodeService.getEpisodesByListId(idList.joinToString(","))
    }

    suspend fun getEpisodesByListIdDb(idList: List<Int>): List<EpisodeEntityDb> {
        return episodeDao.getEpisodesByIds(idList)
    }

    suspend fun getEpisodeListDb(filter: EpisodeFilter): List<EpisodeEntityDb> {
        return episodeDao.getFilteredEpisodeList(filter.name, filter.episode)
    }

    suspend fun insertEpisodesListDb(list: List<EpisodeEntityDb>) {
        episodeDao.insertEpisodeList(list)
    }
}