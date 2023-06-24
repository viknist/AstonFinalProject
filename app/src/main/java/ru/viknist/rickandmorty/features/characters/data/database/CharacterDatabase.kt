package ru.viknist.rickandmorty.features.characters.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.viknist.rickandmorty.features.characters.data.database.dao.CharacterDao
import ru.viknist.rickandmorty.features.characters.data.database.entity.CharacterEntityDb

@Database(
    entities = [CharacterEntityDb::class],
    version = 1,
    exportSchema = false
)
abstract class CharacterDatabase : RoomDatabase() {

    abstract fun getCharacterDao(): CharacterDao
}