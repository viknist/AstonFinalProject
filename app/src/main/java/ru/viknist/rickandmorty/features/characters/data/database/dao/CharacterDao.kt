package ru.viknist.rickandmorty.features.characters.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.viknist.rickandmorty.features.characters.data.database.entity.CharacterEntityDb

@Dao
interface CharacterDao {

    @Insert(entity = CharacterEntityDb::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterList(characterEntity: List<CharacterEntityDb>)

    @Query(SELECT_BY_FILTER_CHARACTER)
    suspend fun getFilteredCharacterList(
        name: String,
        status: String,
        species: String,
        type: String,
        gender: String
    ): List<CharacterEntityDb>

    @Query(SELECT_BY_IDS_CHARACTER)
    suspend fun getCharactersByIds(ids: List<Int>): List<CharacterEntityDb>

    @Query(SELECT_SINGLE_CHARACTER)
    suspend fun getSingleCharacter(id: Int): CharacterEntityDb

    companion object {
        private const val CHARACTER_TABLE_NAME = "character_table"

        private const val SELECT_SINGLE_CHARACTER = "SELECT * " +
                "FROM $CHARACTER_TABLE_NAME " +
                "WHERE id = :id LIMIT 1"

        private const val SELECT_BY_IDS_CHARACTER = "SELECT * " +
                "FROM $CHARACTER_TABLE_NAME " +
                "WHERE id IN (:ids)"

        private const val SELECT_BY_FILTER_CHARACTER = "SELECT * " +
                "FROM $CHARACTER_TABLE_NAME " +
                "WHERE name LIKE ('%' || :name || '%') " +
                "AND status LIKE ('%' || :status || '%') " +
                "AND species LIKE ('%' || :species || '%') " +
                "AND type LIKE ('%' || :type || '%') " +
                "AND gender LIKE ('%' || :gender || '%')"
    }
}