package ru.viknist.rickandmorty.features.locations.domain.usecase

import ru.viknist.rickandmorty.baseapp.getIntFromUrl
import ru.viknist.rickandmorty.features.locations.data.dto.LocationResponse
import ru.viknist.rickandmorty.features.locations.data.repository.LocationRepository
import ru.viknist.rickandmorty.features.locations.domain.entity.LocationEntity
import javax.inject.Inject

class GetLocationDetailsWebUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(id: Int): LocationEntity {
        return map(locationRepository.getLocationByIdWeb(id))
    }

    private fun map(location: LocationResponse): LocationEntity {
        return LocationEntity(
            location.id,
            location.name,
            location.type,
            location.dimension,
            location.residents.map { location -> location.getIntFromUrl() },
            location.url,
            location.created
        )
    }
}