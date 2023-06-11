package ru.viknist.rickandmorty.features.characters.data.repository

import ru.viknist.rickandmorty.features.characters.data.dto.CharacterFilter
import ru.viknist.rickandmorty.features.characters.data.dto.CharacterModel
import ru.viknist.rickandmorty.features.characters.data.dto.CharactersResultModel
import ru.viknist.rickandmorty.features.characters.data.service.CharacterService
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterService: CharacterService
){

    fun getCharacterListWeb(filter: CharacterFilter): CharactersResultModel{
        return characterService.getCharacters(
            filter.name,
            filter.status.toString().lowercase(),
            filter.species,
            filter.type,
            filter.gender.toString().lowercase()
        )
    }

    fun getCharacterByIdWeb(id: Int): CharacterModel{
        return characterService.getCharacterById(id)
    }

    fun getCharactersByListIdWeb(idList: List<Int>): List<CharacterModel>{
        return characterService.getCharacterByListId(idList)
    }
}