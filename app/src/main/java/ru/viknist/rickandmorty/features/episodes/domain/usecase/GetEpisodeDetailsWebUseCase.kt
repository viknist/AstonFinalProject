package ru.viknist.rickandmorty.features.episodes.domain.usecase

import ru.viknist.rickandmorty.baseapp.getIntFromUrl
import ru.viknist.rickandmorty.features.episodes.data.dto.EpisodeResponse
import ru.viknist.rickandmorty.features.episodes.data.repository.EpisodeRepository
import ru.viknist.rickandmorty.features.episodes.domain.entity.EpisodeEntity
import javax.inject.Inject

class GetEpisodeDetailsWebUseCase @Inject constructor(
    private val episodeRepository: EpisodeRepository
) {

    suspend operator fun invoke(id: Int): EpisodeEntity {
        return map(episodeRepository.getEpisodeByIdWeb(id))
    }

    private fun map(episode: EpisodeResponse): EpisodeEntity {
        return EpisodeEntity(
            episode.id,
            episode.name,
            episode.air_date,
            episode.episode,
            episode.characters.map { character -> character.getIntFromUrl() },
            episode.url,
            episode.created
        )
    }
}