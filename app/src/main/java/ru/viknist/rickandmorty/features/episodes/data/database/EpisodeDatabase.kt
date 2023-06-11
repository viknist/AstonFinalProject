package ru.viknist.rickandmorty.features.episodes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.viknist.rickandmorty.features.episodes.data.database.dao.EpisodeDao
import ru.viknist.rickandmorty.features.episodes.data.database.entity.EpisodeEntityDb

@Database(
    entities = [EpisodeEntityDb::class],
    version = 1,
    exportSchema = false
)
abstract class EpisodeDatabase : RoomDatabase() {

    abstract fun getEpisodeDao(): EpisodeDao
}