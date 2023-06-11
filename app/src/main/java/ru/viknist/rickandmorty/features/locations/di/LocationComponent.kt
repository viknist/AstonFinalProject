package ru.viknist.rickandmorty.features.locations.di

import dagger.Component

@Component(
    modules = [LocationModule::class]
)
interface LocationComponent {

    companion object {
        fun init(): LocationComponent {
            return DaggerLocationComponent.factory()
                .create()
        }
    }

    @Component.Factory
    interface Factory {

        fun create(): LocationComponent
    }
}