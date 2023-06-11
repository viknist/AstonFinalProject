package ru.viknist.rickandmorty.features.characters.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.viknist.rickandmorty.core.DependenciesProvider
import ru.viknist.rickandmorty.core.di.ViewModelFactoryModule
import ru.viknist.rickandmorty.features.characters.presentation.CharacterDetailsFragment
import ru.viknist.rickandmorty.features.characters.presentation.CharactersListFragment
import ru.viknist.rickandmorty.features.episodes.di.EpisodeModule

@Component(
    dependencies = [DependenciesProvider::class],
    modules = [EpisodeModule::class, CharacterModule::class, ViewModelFactoryModule::class, CharacterViewModelModule::class]
)
interface CharacterComponent {

    companion object {
        fun init(
            dependenciesProvider: DependenciesProvider, context: Context
        ): CharacterComponent {
            return DaggerCharacterComponent.factory()
                .create(dependenciesProvider = dependenciesProvider, context = context)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            dependenciesProvider: DependenciesProvider, @BindsInstance context: Context
        ): CharacterComponent
    }

    fun injectCharactersList(charactersListFragment: CharactersListFragment)

    fun injectCharacterDetails(characterDetailsFragment: CharacterDetailsFragment)
}