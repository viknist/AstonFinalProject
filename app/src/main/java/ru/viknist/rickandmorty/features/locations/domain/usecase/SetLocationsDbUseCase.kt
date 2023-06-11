package ru.viknist.rickandmorty.features.locations.domain.usecase

import ru.viknist.rickandmorty.features.locations.data.database.entity.LocationEntityDb
import ru.viknist.rickandmorty.features.locations.data.repository.LocationRepository
import ru.viknist.rickandmorty.features.locations.domain.entity.LocationData
import javax.inject.Inject

class SetLocationsDbUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(data: LocationData) {
        locationRepository.insertLocationsListDb(map(data))
    }

    private fun map(data: LocationData): List<LocationEntityDb> {
        return data.locationList.map {
            LocationEntityDb(
                it.id,
                it.name,
                it.type,
                it.dimension,
                it.residents.joinToString(" "),
                it.url,
                it.created
            )
        }
    }
}