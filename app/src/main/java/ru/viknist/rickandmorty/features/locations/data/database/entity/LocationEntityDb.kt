package ru.viknist.rickandmorty.features.locations.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_table")
data class LocationEntityDb(
    @PrimaryKey
    val id: Int,
    var name: String,
    var type: String,
    var dimension: String,
    var residents: String,
    var url: String,
    var created: String
)