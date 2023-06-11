package ru.viknist.rickandmorty.features.episodes.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.viknist.rickandmorty.core.DependenciesProvider
import ru.viknist.rickandmorty.core.di.ViewModelFactoryModule
import ru.viknist.rickandmorty.features.characters.di.CharacterModule
import ru.viknist.rickandmorty.features.episodes.presentation.EpisodeDetailsFragment
import ru.viknist.rickandmorty.features.episodes.presentation.EpisodesListFragment

@Component(
    dependencies = [DependenciesProvider::class],
    modules = [
        CharacterModule::class,
        EpisodeModule::class,
        ViewModelFactoryModule::class,
        EpisodeViewModelModule::class
    ]
)
interface EpisodeComponent {

    companion object {
        fun init(
            dependenciesProvider: DependenciesProvider,
            context: Context
        ): EpisodeComponent {
            return DaggerEpisodeComponent.factory()
                .create(dependenciesProvider, context)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            dependenciesProvider: DependenciesProvider,
            @BindsInstance context: Context
        ): EpisodeComponent
    }

    fun injectEpisodesList(episodesListFragment: EpisodesListFragment)

    fun injectEpisodeDetails(episodeDetailsFragment: EpisodeDetailsFragment)
}