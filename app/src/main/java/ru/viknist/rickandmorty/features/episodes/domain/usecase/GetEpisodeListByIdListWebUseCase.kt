package ru.viknist.rickandmorty.features.episodes.domain.usecase

import ru.viknist.rickandmorty.baseapp.getIntFromUrl
import ru.viknist.rickandmorty.features.episodes.data.dto.EpisodeResponse
import ru.viknist.rickandmorty.features.episodes.data.repository.EpisodeRepository
import ru.viknist.rickandmorty.features.episodes.domain.entity.EpisodeEntity
import javax.inject.Inject

class GetEpisodeListByIdListWebUseCase @Inject constructor(
    private val episodeRepository: EpisodeRepository
) {
    suspend operator fun invoke(list: List<Int>): List<EpisodeEntity> {
        return map(episodeRepository.getEpisodesByListIdWeb(list))
    }

    private fun map(episodes: List<EpisodeResponse>): List<EpisodeEntity> {
        return episodes.map {
            EpisodeEntity(
                it.id,
                it.name,
                it.air_date,
                it.episode,
                it.characters.map { character -> character.getIntFromUrl() },
                it.url,
                it.created
            )
        }
    }
}