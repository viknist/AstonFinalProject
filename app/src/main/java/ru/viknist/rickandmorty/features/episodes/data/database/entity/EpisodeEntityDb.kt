package ru.viknist.rickandmorty.features.episodes.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episode_table")
data class EpisodeEntityDb(
    @PrimaryKey
    val id: Int,
    var name: String,
    var air_date: String,
    var episode: String,
    var characters: String,
    var url: String,
    var created: String
)