package ru.viknist.rickandmorty.features.locations.data.dto

import ru.viknist.rickandmorty.baseapp.data.dto.ResultInfoResponse

data class LocationsResultResponse(
    val info: ResultInfoResponse,
    val results: List<LocationResponse>
)
