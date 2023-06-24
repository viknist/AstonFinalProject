package ru.viknist.rickandmorty.features.episodes.domain.entity

data class EpisodeEntity(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<Int>,
    val url: String,
    val created: String
)