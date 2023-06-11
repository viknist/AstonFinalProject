package ru.viknist.rickandmorty.features.episodes.domain.usecase

import ru.viknist.rickandmorty.features.episodes.data.database.entity.EpisodeEntityDb
import ru.viknist.rickandmorty.features.episodes.data.repository.EpisodeRepository
import ru.viknist.rickandmorty.features.episodes.domain.entity.EpisodeData
import javax.inject.Inject

class SetEpisodesDbUseCase @Inject constructor(
    private val episodeRepository: EpisodeRepository
) {
    suspend operator fun invoke(data: EpisodeData) {
        episodeRepository.insertEpisodesListDb(map(data))
    }

    private fun map(data: EpisodeData): List<EpisodeEntityDb> {
        return data.episodeList.map {
            EpisodeEntityDb(
                it.id,
                it.name,
                it.air_date,
                it.episode,
                it.characters.joinToString(" "),
                it.url,
                it.created
            )
        }
    }
}