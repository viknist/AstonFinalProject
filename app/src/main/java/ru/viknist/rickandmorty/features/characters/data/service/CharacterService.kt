package ru.viknist.rickandmorty.features.characters.data.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.viknist.rickandmorty.features.characters.data.dto.CharacterModel
import ru.viknist.rickandmorty.features.characters.data.dto.CharactersResultModel

interface CharacterService {

    @GET("character/")
    fun getCharacters(
        @Query("name") name: String,
        @Query("status") status: String,
        @Query("species") species: String,
        @Query("type") type: String,
        @Query("gender") gender: String
    ): CharactersResultModel

    @GET("character/{id}/")
    fun getCharacterById(
        @Path("id") id: Int
    ): CharacterModel

    @GET("character/{id}/")
    fun getCharacterByListId(
        @Path("id") idList: List<Int>
    ): List<CharacterModel>
}