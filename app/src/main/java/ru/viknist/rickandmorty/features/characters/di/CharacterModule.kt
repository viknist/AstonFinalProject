package ru.viknist.rickandmorty.features.characters.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.viknist.rickandmorty.features.characters.data.database.CharacterDatabase
import ru.viknist.rickandmorty.features.characters.data.repository.CharacterRepository
import ru.viknist.rickandmorty.features.characters.data.service.CharacterService

@Module
class CharacterModule {

    @Provides
    fun provideCharacterService(
        retrofit: Retrofit
    ): CharacterService {
        return retrofit.create(CharacterService::class.java)
    }

    @Provides
    fun provideCharacterDataBase(context: Context): CharacterDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CharacterDatabase::class.java,
            "characters_db"
        ).build()
    }

    @Provides
    fun provideCharacterRepository(
        service: CharacterService,
        dataBase: CharacterDatabase
    ): CharacterRepository {
        return CharacterRepository(service, dataBase.getCharacterDao())
    }
}