package ru.viknist.rickandmorty.features.characters.di

import dagger.Component

@Component(
    modules = [CharacterModule::class]
)
interface CharacterComponent {

    companion object {
        fun init(): CharacterComponent {
            return DaggerCharacterComponent.factory()
                .create()
        }
    }

    @Component.Factory
    interface Factory {

        fun create(): CharacterComponent
    }
}