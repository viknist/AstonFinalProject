package ru.viknist.rickandmorty.features.locations.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.viknist.rickandmorty.core.di.ViewModelKey
import ru.viknist.rickandmorty.features.locations.presentation.LocationDetailsViewModel
import ru.viknist.rickandmorty.features.locations.presentation.LocationListViewModel

@Module
interface LocationViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LocationListViewModel::class)
    fun bindEpisodeListViewModel(locationListViewModel: LocationListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationDetailsViewModel::class)
    fun bindEpisodeDetailsViewModel(locationDetailsViewModel: LocationDetailsViewModel): ViewModel
}