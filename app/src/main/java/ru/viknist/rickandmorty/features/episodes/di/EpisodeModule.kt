package ru.viknist.rickandmorty.features.episodes.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.viknist.rickandmorty.features.episodes.data.service.EpisodeService

@Module
class EpisodeModule {

    @Provides
    fun provideEpisodeService(
        retrofit: Retrofit
    ): EpisodeService {
        return retrofit.create(EpisodeService::class.java)
    }
}