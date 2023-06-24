package ru.viknist.rickandmorty.features.characters.data.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.viknist.rickandmorty.features.characters.data.dto.CharacterResponse
import ru.viknist.rickandmorty.features.characters.data.dto.CharactersResultResponse

interface CharacterService {

    @GET("character/")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("status") status: String,
        @Query("species") species: String,
        @Query("type") type: String,
        @Query("gender") gender: String
    ): CharactersResultResponse

    @GET("character/{id}/")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): CharacterResponse

    @GET("character/{id}/")
    suspend fun getCharacterByListId(
        @Path("id") idList: String
    ): List<CharacterResponse>
}