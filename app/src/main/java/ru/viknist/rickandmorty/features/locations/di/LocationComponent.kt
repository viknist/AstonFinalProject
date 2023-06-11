package ru.viknist.rickandmorty.features.locations.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.viknist.rickandmorty.core.DependenciesProvider
import ru.viknist.rickandmorty.core.di.ViewModelFactoryModule
import ru.viknist.rickandmorty.features.characters.di.CharacterModule
import ru.viknist.rickandmorty.features.locations.presentation.LocationDetailsFragment
import ru.viknist.rickandmorty.features.locations.presentation.LocationsListFragment

@Component(
    dependencies = [DependenciesProvider::class],
    modules = [
        CharacterModule::class,
        LocationModule::class,
        ViewModelFactoryModule::class,
        LocationViewModelModule::class
    ]
)
interface LocationComponent {

    companion object {
        fun init(
            dependenciesProvider: DependenciesProvider,
            context: Context
        ): LocationComponent {
            return DaggerLocationComponent.factory()
                .create(dependenciesProvider, context)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            dependenciesProvider: DependenciesProvider,
            @BindsInstance context: Context
        ): LocationComponent
    }

    fun injectLocationsList(locationsListFragment: LocationsListFragment)

    fun injectLocationDetails(locationDetailsFragment: LocationDetailsFragment)
}