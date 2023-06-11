package ru.viknist.rickandmorty.features.locations.domain.entity

import ru.viknist.rickandmorty.baseapp.domain.entity.PageInfo

data class LocationData(
    val info: PageInfo,
    val locationList: List<LocationEntity>
)
