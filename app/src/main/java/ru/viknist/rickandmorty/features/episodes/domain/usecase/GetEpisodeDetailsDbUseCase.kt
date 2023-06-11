package ru.viknist.rickandmorty.features.episodes.domain.usecase

import ru.viknist.rickandmorty.features.episodes.data.database.entity.EpisodeEntityDb
import ru.viknist.rickandmorty.features.episodes.data.repository.EpisodeRepository
import ru.viknist.rickandmorty.features.episodes.domain.entity.EpisodeEntity
import javax.inject.Inject

class GetEpisodeDetailsDbUseCase @Inject constructor(
    private val episodeRepository: EpisodeRepository
) {

    suspend operator fun invoke(id: Int): EpisodeEntity {
        return map(episodeRepository.getEpisodeByIdDb(id))
    }

    private fun map(episode: EpisodeEntityDb): EpisodeEntity {
        return EpisodeEntity(
            episode.id,
            episode.name,
            episode.air_date,
            episode.episode,
            episode.characters.split(" ").map { num -> num.toInt() },
            episode.url,
            episode.created
        )
    }
}