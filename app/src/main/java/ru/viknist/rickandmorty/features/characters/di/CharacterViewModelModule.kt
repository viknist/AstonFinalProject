package ru.viknist.rickandmorty.features.characters.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.viknist.rickandmorty.core.di.ViewModelKey
import ru.viknist.rickandmorty.features.characters.presentation.CharacterDetailsViewModel
import ru.viknist.rickandmorty.features.characters.presentation.CharacterListViewModel

@Module
interface CharacterViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CharacterListViewModel::class)
    fun bindCharacterListViewModel(characterListViewModel: CharacterListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailsViewModel::class)
    fun bindCharacterDetailsViewModel(characterDetailsViewModel: CharacterDetailsViewModel): ViewModel
}