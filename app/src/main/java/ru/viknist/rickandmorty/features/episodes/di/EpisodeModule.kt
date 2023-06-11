package ru.viknist.rickandmorty.features.episodes.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.viknist.rickandmorty.features.episodes.data.database.EpisodeDatabase
import ru.viknist.rickandmorty.features.episodes.data.repository.EpisodeRepository
import ru.viknist.rickandmorty.features.episodes.data.service.EpisodeService

@Module
class EpisodeModule {

    @Provides
    fun provideEpisodeService(
        retrofit: Retrofit
    ): EpisodeService {
        return retrofit.create(EpisodeService::class.java)
    }

    @Provides
    fun provideEpisodeDataBase(context: Context): EpisodeDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            EpisodeDatabase::class.java,
            "episode_db"
        ).build()
    }

    @Provides
    fun provideEpisodeRepository(
        service: EpisodeService,
        dataBase: EpisodeDatabase
    ): EpisodeRepository {
        return EpisodeRepository(service, dataBase.getEpisodeDao())
    }
}