package ru.viknist.rickandmorty.features.episodes.domain.usecase

import ru.viknist.rickandmorty.features.episodes.data.database.entity.EpisodeEntityDb
import ru.viknist.rickandmorty.features.episodes.data.repository.EpisodeRepository
import ru.viknist.rickandmorty.features.episodes.domain.entity.EpisodeEntity
import javax.inject.Inject

class GetEpisodeListByIdListDbUseCase @Inject constructor(
    private val episodeRepository: EpisodeRepository
) {
    suspend operator fun invoke(list: List<Int>): List<EpisodeEntity> {
        return map(episodeRepository.getEpisodesByListIdDb(list))
    }

    private fun map(episodes: List<EpisodeEntityDb>): List<EpisodeEntity> {
        return episodes.map {
            EpisodeEntity(
                it.id,
                it.name,
                it.air_date,
                it.episode,
                it.characters.split(" ").map { num -> num.toInt() },
                it.url,
                it.created
            )
        }
    }
}