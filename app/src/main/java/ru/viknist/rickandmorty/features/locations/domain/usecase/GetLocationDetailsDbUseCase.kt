package ru.viknist.rickandmorty.features.locations.domain.usecase

import ru.viknist.rickandmorty.features.locations.data.database.entity.LocationEntityDb
import ru.viknist.rickandmorty.features.locations.data.repository.LocationRepository
import ru.viknist.rickandmorty.features.locations.domain.entity.LocationEntity
import javax.inject.Inject

class GetLocationDetailsDbUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke(id: Int): LocationEntity {
        return map(locationRepository.getLocationByIdDb(id))
    }

    private fun map(location: LocationEntityDb): LocationEntity {
        return LocationEntity(
            location.id,
            location.name,
            location.type,
            location.dimension,
            try {
                location.residents.split(" ").map { num -> num.toInt() }
            } catch (e: NumberFormatException) {
                emptyList()
            },
            location.url,
            location.created
        )
    }

}