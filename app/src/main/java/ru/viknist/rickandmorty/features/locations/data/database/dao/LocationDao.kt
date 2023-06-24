package ru.viknist.rickandmorty.features.locations.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.viknist.rickandmorty.features.locations.data.database.entity.LocationEntityDb

@Dao
interface LocationDao {

    @Insert(entity = LocationEntityDb::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationList(episodeEntity: List<LocationEntityDb>)

    @Query(SELECT_BY_FILTER_LOCATION)
    suspend fun getFilteredLocationList(
        name: String,
        type: String,
        dimension: String
    ): List<LocationEntityDb>

    @Query(SELECT_BY_IDS_LOCATION)
    suspend fun getLocationsByIds(ids: List<Int>): List<LocationEntityDb>

    @Query(SELECT_SINGLE_LOCATION)
    suspend fun getSingleLocation(id: Int): LocationEntityDb

    companion object {
        private const val LOCATION_TABLE_NAME = "location_table"

        private const val SELECT_SINGLE_LOCATION = "SELECT * " +
                "FROM $LOCATION_TABLE_NAME " +
                "WHERE id = :id LIMIT 1"

        private const val SELECT_BY_IDS_LOCATION = "SELECT * " +
                "FROM $LOCATION_TABLE_NAME " +
                "WHERE id IN (:ids)"

        private const val SELECT_BY_FILTER_LOCATION = "SELECT * " +
                "FROM $LOCATION_TABLE_NAME " +
                "WHERE  name LIKE ('%' || :name || '%') " +
                "AND type LIKE ('%' || :type || '%') " +
                "AND dimension LIKE ('%' || :dimension || '%')"
    }
}