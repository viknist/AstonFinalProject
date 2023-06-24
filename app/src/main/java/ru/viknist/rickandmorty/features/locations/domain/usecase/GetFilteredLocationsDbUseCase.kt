package ru.viknist.rickandmorty.features.locations.domain.usecase

import ru.viknist.rickandmorty.baseapp.domain.entity.PageInfo
import ru.viknist.rickandmorty.features.locations.data.database.entity.LocationEntityDb
import ru.viknist.rickandmorty.features.locations.data.repository.LocationRepository
import ru.viknist.rickandmorty.features.locations.domain.entity.LocationData
import ru.viknist.rickandmorty.features.locations.domain.entity.LocationEntity
import ru.viknist.rickandmorty.features.locations.models.LocationFilter

import javax.inject.Inject

class GetFilteredLocationsDbUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(filter: LocationFilter): LocationData {

        return map(locationRepository.getLocationListDb(filter))
    }

    private fun map(locations: List<LocationEntityDb>): LocationData {
        return LocationData(
            PageInfo(
                locations.size,
                1,
                null,
                null
            ),
            locations.map {
                LocationEntity(
                    it.id,
                    it.name,
                    it.type,
                    it.dimension,
                    try {
                        it.residents.split(" ").map { num -> num.toInt() }
                    } catch (e: NumberFormatException) {
                        emptyList()
                    },
                    it.url,
                    it.created
                )
            }
        )
    }
}