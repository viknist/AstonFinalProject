package ru.viknist.rickandmorty.features.locations.data.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.viknist.rickandmorty.features.locations.data.dto.LocationResponse
import ru.viknist.rickandmorty.features.locations.data.dto.LocationsResultResponse

interface LocationService {

    @GET("location/")
    suspend fun getLocations(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("type") type: String,
        @Query("dimension") dimension: String
    ): LocationsResultResponse

    @GET("location/{id}")
    suspend fun getLocationById(
        @Path("id") id: Int
    ): LocationResponse

    @GET("location/{id}")
    suspend fun getLocationByListId(
        @Path("id") idList: List<Int>
    ): List<LocationResponse>
}