package ru.viknist.rickandmorty.features.episodes.data.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.viknist.rickandmorty.features.episodes.data.dto.EpisodeResponse
import ru.viknist.rickandmorty.features.episodes.data.dto.EpisodesResultResponse

interface EpisodeService {

    @GET("episode/")
    suspend fun getEpisodes(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("episode") episode: String
    ): EpisodesResultResponse

    @GET("episode/{id}")
    suspend fun getEpisodeById(
        @Path("id") id: Int,
    ): EpisodeResponse

    @GET("episode/{id}")
    suspend fun getEpisodesByListId(
        @Path("id") idList: String
    ): List<EpisodeResponse>

}