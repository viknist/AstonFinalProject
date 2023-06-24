package ru.viknist.rickandmorty.features.locations.domain.entity

data class LocationEntity(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<Int>,
    val url: String,
    val created: String
)
