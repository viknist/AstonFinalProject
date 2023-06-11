package ru.viknist.rickandmorty.features.episodes.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.viknist.rickandmorty.features.episodes.data.database.entity.EpisodeEntityDb

@Dao
interface EpisodeDao {

    @Insert(entity = EpisodeEntityDb::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodeList(episodeEntity: List<EpisodeEntityDb>)

    @Query(SELECT_BY_FILTER_EPISODE)
    suspend fun getFilteredEpisodeList(
        name: String,
        episode: String
    ): List<EpisodeEntityDb>

    @Query(SELECT_BY_IDS_EPISODE)
    suspend fun getEpisodesByIds(ids: List<Int>): List<EpisodeEntityDb>

    @Query(SELECT_SINGLE_EPISODE)
    suspend fun getSingleEpisode(id: Int): EpisodeEntityDb

    companion object {
        private const val EPISODE_TABLE_NAME = "episode_table"

        private const val SELECT_SINGLE_EPISODE = "SELECT * " +
                "FROM $EPISODE_TABLE_NAME " +
                "WHERE id = :id LIMIT 1"

        private const val SELECT_BY_IDS_EPISODE = "SELECT * " +
                "FROM $EPISODE_TABLE_NAME " +
                "WHERE id IN (:ids)"

        private const val SELECT_BY_FILTER_EPISODE = "SELECT * " +
                "FROM $EPISODE_TABLE_NAME " +
                "WHERE  name LIKE ('%' || :name || '%') " +
                "AND episode LIKE ('%' || :episode || '%')"
    }
}