package ru.viknist.rickandmorty.features.episodes.data.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.viknist.rickandmorty.features.episodes.data.dto.EpisodeModel
import ru.viknist.rickandmorty.features.episodes.data.dto.EpisodesResultModel

interface EpisodeService {

    @GET("episode/")
    fun getEpisodes(
        @Query("name") name: String,
        @Query("episode") episode: String
    ): EpisodesResultModel

    @GET("episode/{id}")
    fun getEpisodeById(
        @Path("id") id: Int,
    ): EpisodeModel

    @GET("episode/{id}")
    fun getEpisodesByListId(
        @Path("id") idList: List<Int>
    ): List<EpisodeModel>

}