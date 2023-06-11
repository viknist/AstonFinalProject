package ru.viknist.rickandmorty.features.locations.data.repository

import ru.viknist.rickandmorty.features.locations.data.database.dao.LocationDao
import ru.viknist.rickandmorty.features.locations.data.database.entity.LocationEntityDb
import ru.viknist.rickandmorty.features.locations.models.LocationFilter
import ru.viknist.rickandmorty.features.locations.data.dto.LocationResponse
import ru.viknist.rickandmorty.features.locations.data.dto.LocationsResultResponse
import ru.viknist.rickandmorty.features.locations.data.service.LocationService
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationService: LocationService,
    private val locationDao: LocationDao
) {

    suspend fun getLocationListWeb(filter: LocationFilter, page: Int): LocationsResultResponse {
        return locationService.getLocations(
            page,
            filter.name,
            filter.type,
            filter.dimension
        )
    }

    suspend fun getLocationByIdWeb(id: Int): LocationResponse {
        return locationService.getLocationById(id)
    }

    suspend fun getLocationByIdDb(id: Int): LocationEntityDb {
        return locationDao.getSingleLocation(id)
    }

    suspend fun getLocationsByListIdWeb(idList: List<Int>): List<LocationResponse> {
        return locationService.getLocationByListId(idList)
    }

    suspend fun getLocationListDb(filter: LocationFilter): List<LocationEntityDb> {
        val list = locationDao.getFilteredLocationList(filter.name, filter.type, filter.dimension)
        println(list.forEach { println(it) })
        return list
    }

    suspend fun insertLocationsListDb(list: List<LocationEntityDb>) {
        locationDao.insertLocationList(list)
    }
}