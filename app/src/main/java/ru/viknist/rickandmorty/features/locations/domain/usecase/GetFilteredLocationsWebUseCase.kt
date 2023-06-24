package ru.viknist.rickandmorty.features.locations.domain.usecase

import ru.viknist.rickandmorty.baseapp.domain.entity.PageInfo
import ru.viknist.rickandmorty.baseapp.getIntFromUrl
import ru.viknist.rickandmorty.features.locations.data.dto.LocationsResultResponse
import ru.viknist.rickandmorty.features.locations.data.repository.LocationRepository
import ru.viknist.rickandmorty.features.locations.domain.entity.LocationData
import ru.viknist.rickandmorty.features.locations.domain.entity.LocationEntity
import ru.viknist.rickandmorty.features.locations.models.LocationFilter

import javax.inject.Inject

class GetFilteredLocationsWebUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(filter: LocationFilter, page: Int): LocationData {
        return map(locationRepository.getLocationListWeb(filter, page))
    }

    private fun map(locationResultResponse: LocationsResultResponse): LocationData {
        return LocationData(
            PageInfo(
                locationResultResponse.info.count,
                locationResultResponse.info.pages,
                locationResultResponse.info.next,
                locationResultResponse.info.prev
            ),
            locationResultResponse.results.map {
                LocationEntity(
                    it.id,
                    it.name,
                    it.type,
                    it.dimension,
                    it.residents.map { location -> location.getIntFromUrl() },
                    it.url,
                    it.created
                )
            }
        )
    }
}