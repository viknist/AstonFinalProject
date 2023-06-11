package ru.viknist.rickandmorty.features.episodes.di

import dagger.Component

@Component(
    modules = [EpisodeModule::class]
)
interface EpisodeComponent {

    companion object {
        fun init(): EpisodeComponent {
            return DaggerEpisodeComponent.factory()
                .create()
        }
    }

    @Component.Factory
    interface Factory {

        fun create(): EpisodeComponent
    }
}