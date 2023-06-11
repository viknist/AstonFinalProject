package ru.viknist.rickandmorty.features.episodes.domain.usecase

import ru.viknist.rickandmorty.baseapp.domain.entity.PageInfo
import ru.viknist.rickandmorty.features.episodes.data.database.entity.EpisodeEntityDb
import ru.viknist.rickandmorty.features.episodes.data.repository.EpisodeRepository
import ru.viknist.rickandmorty.features.episodes.domain.entity.EpisodeData
import ru.viknist.rickandmorty.features.episodes.domain.entity.EpisodeEntity
import ru.viknist.rickandmorty.features.episodes.models.EpisodeFilter
import javax.inject.Inject

class GetFilteredEpisodesDbUseCase @Inject constructor(
    private val episodeRepository: EpisodeRepository
) {
    suspend operator fun invoke(filter: EpisodeFilter): EpisodeData {
        return map(episodeRepository.getEpisodeListDb(filter))
    }

    private fun map(episodes: List<EpisodeEntityDb>): EpisodeData {
        return EpisodeData(
            PageInfo(
                episodes.size,
                1,
                null,
                null
            ),
            episodes.map {
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
        )
    }
}