package ru.viknist.rickandmorty.features.locations.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.viknist.rickandmorty.features.locations.data.database.LocationDatabase
import ru.viknist.rickandmorty.features.locations.data.repository.LocationRepository
import ru.viknist.rickandmorty.features.locations.data.service.LocationService

@Module
class LocationModule {

    @Provides
    fun provideLocationService(
        retrofit: Retrofit
    ): LocationService {
        return retrofit.create(LocationService::class.java)
    }

    @Provides
    fun provideLocationDataBase(context: Context): LocationDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            LocationDatabase::class.java,
            "location_db"
        ).build()
    }

    @Provides
    fun provideLocationRepository(
        service: LocationService,
        dataBase: LocationDatabase
    ): LocationRepository {
        return LocationRepository(service, dataBase.getLocationDao())
    }
}