package ru.viknist.rickandmorty.features.locations.data.repository

import ru.viknist.rickandmorty.features.locations.data.dto.LocationFilter
import ru.viknist.rickandmorty.features.locations.data.dto.LocationModel
import ru.viknist.rickandmorty.features.locations.data.dto.LocationsResultModel
import ru.viknist.rickandmorty.features.locations.data.service.LocationService
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationService: LocationService
){

    fun getLocationListWeb(filter: LocationFilter): LocationsResultModel{
        return locationService.getLocations(
            filter.name,
            filter.type,
            filter.dimension
        )
    }

    fun getLocationByIdWeb(id: Int): LocationModel{
        return locationService.getLocationById(id)
    }

    fun getLocationsByListIdWeb(idList: List<Int>): List<LocationModel>{
        return locationService.getLocationByListId(idList)
    }
}