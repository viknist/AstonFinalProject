package ru.viknist.rickandmorty.features.locations.models

data class LocationFilter(
    val name: String,
    val type: String,
    val dimension: String
) : java.io.Serializable
