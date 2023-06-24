package ru.viknist.rickandmorty.features.locations.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.viknist.rickandmorty.features.locations.data.database.dao.LocationDao
import ru.viknist.rickandmorty.features.locations.data.database.entity.LocationEntityDb

@Database(
    entities = [LocationEntityDb::class],
    version = 1,
    exportSchema = false
)
abstract class LocationDatabase : RoomDatabase() {

    abstract fun getLocationDao(): LocationDao
}