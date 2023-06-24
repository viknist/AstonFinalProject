package ru.viknist.rickandmorty.features.characters.data.repository

import ru.viknist.rickandmorty.features.characters.data.database.dao.CharacterDao
import ru.viknist.rickandmorty.features.characters.data.database.entity.CharacterEntityDb
import ru.viknist.rickandmorty.features.characters.models.CharacterFilter
import ru.viknist.rickandmorty.features.characters.data.dto.CharacterResponse
import ru.viknist.rickandmorty.features.characters.data.dto.CharactersResultResponse
import ru.viknist.rickandmorty.features.characters.data.service.CharacterService
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterService: CharacterService,
    private val characterDao: CharacterDao
) {

    suspend fun getCharacterListWeb(filter: CharacterFilter, page: Int): CharactersResultResponse {
        return characterService.getCharacters(
            page,
            filter.name,
            if (filter.status != null) {
                filter.status.toString().lowercase()
            } else {
                ""
            },
            filter.species,
            filter.type,
            if (filter.gender != null) {
                filter.gender.toString().lowercase()
            } else {
                ""
            }
        )
    }

    suspend fun getCharacterByIdWeb(id: Int): CharacterResponse {
        return characterService.getCharacterById(id)
    }

    suspend fun getCharacterByIdDb(id: Int): CharacterEntityDb {
        return characterDao.getSingleCharacter(id)
    }

    suspend fun getCharactersByListIdWeb(idList: List<Int>): List<CharacterResponse> {
        return characterService.getCharacterByListId(idList.joinToString(","))
    }

    suspend fun getCharactersByListIdDb(idList: List<Int>): List<CharacterEntityDb> {
        return characterDao.getCharactersByIds(idList)
    }

    suspend fun getCharactersListDb(filter: CharacterFilter): List<CharacterEntityDb> {
        return characterDao.getFilteredCharacterList(
            filter.name,
            if (filter.status != null) {
                filter.status.toString().lowercase()
            } else {
                ""
            },
            filter.species,
            filter.type,
            if (filter.gender != null) {
                filter.gender.toString().lowercase()
            } else {
                ""
            }
        )
    }

    suspend fun insertCharactersListDb(list: List<CharacterEntityDb>) {
        characterDao.insertCharacterList(list)
    }
}