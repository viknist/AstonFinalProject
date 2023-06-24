package ru.viknist.rickandmorty.features.characters.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_table")
data class CharacterEntityDb(
    @PrimaryKey()
    val id: Int,
    var name: String,
    var status: String,
    var species: String,
    var type: String,
    var gender: String,
    @Embedded(prefix = "origin")
    var origin: CharacterLocationEntity,
    @Embedded(prefix = "location")
    var location: CharacterLocationEntity,
    var image: String,
    var episode: String,
    var url: String,
    var created: String
)

data class CharacterLocationEntity(
    val id: Int,
    var name: String
)