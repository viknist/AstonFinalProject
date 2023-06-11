package ru.viknist.rickandmorty.features.locations.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.viknist.rickandmorty.features.locations.data.service.LocationService

@Module
class LocationModule {

    @Provides
    fun provideLocationService(
        retrofit: Retrofit
    ): LocationService {
        return retrofit.create(LocationService::class.java)
    }
}