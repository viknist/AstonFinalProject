package ru.viknist.rickandmorty.features.episodes.domain.usecase

import ru.viknist.rickandmorty.baseapp.domain.entity.PageInfo
import ru.viknist.rickandmorty.baseapp.getIntFromUrl
import ru.viknist.rickandmorty.features.episodes.data.dto.EpisodesResultResponse
import ru.viknist.rickandmorty.features.episodes.data.repository.EpisodeRepository
import ru.viknist.rickandmorty.features.episodes.domain.entity.EpisodeData
import ru.viknist.rickandmorty.features.episodes.domain.entity.EpisodeEntity
import ru.viknist.rickandmorty.features.episodes.models.EpisodeFilter
import javax.inject.Inject

class GetFilteredEpisodesWebUseCase @Inject constructor(
    private val episodeRepository: EpisodeRepository
) {
    suspend operator fun invoke(filter: EpisodeFilter, page: Int): EpisodeData {
        return map(episodeRepository.getEpisodeListWeb(filter, page))
    }

    private fun map(episodeResultResponse: EpisodesResultResponse): EpisodeData {
        return EpisodeData(
            PageInfo(
                episodeResultResponse.info.count,
                episodeResultResponse.info.pages,
                episodeResultResponse.info.next,
                episodeResultResponse.info.prev
            ),
            episodeResultResponse.results.map {
                EpisodeEntity(
                    it.id,
                    it.name,
                    it.air_date,
                    it.episode,
                    it.characters.map { episode -> episode.getIntFromUrl() },
                    it.url,
                    it.created
                )
            }
        )
    }
}