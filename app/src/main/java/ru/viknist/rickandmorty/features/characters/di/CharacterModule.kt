package ru.viknist.rickandmorty.features.characters.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.viknist.rickandmorty.features.characters.data.service.CharacterService

@Module
class CharacterModule {

    @Provides
    fun provideCharacterService(
        retrofit: Retrofit
    ): CharacterService {
        return retrofit.create(CharacterService::class.java)
    }
}