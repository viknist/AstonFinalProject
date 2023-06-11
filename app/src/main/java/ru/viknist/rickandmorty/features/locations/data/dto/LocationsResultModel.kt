package ru.viknist.rickandmorty.features.locations.data.dto

import ru.viknist.rickandmorty.features.characters.data.dto.ResultInfoModel

data class LocationsResultModel(
    val info: ResultInfoModel,
    val results: List<LocationModel>
)
