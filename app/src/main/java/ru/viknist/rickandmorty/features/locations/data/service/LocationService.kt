package ru.viknist.rickandmorty.features.locations.data.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.viknist.rickandmorty.features.locations.data.dto.LocationModel
import ru.viknist.rickandmorty.features.locations.data.dto.LocationsResultModel

interface LocationService {

    @GET("location/")
    fun getLocations(
        @Query("name") name: String,
        @Query("type") type: String,
        @Query("dimension") dimension: String
    ): LocationsResultModel

    @GET("location/{id}")
    fun getLocationById(
        @Path("id") id: Int
    ): LocationModel

    @GET("location/{id}")
    fun getLocationByListId(
        @Path("id") idList: List<Int>
    ): List<LocationModel>
}