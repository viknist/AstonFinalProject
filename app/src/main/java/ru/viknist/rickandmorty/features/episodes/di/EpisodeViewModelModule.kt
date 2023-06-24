package ru.viknist.rickandmorty.features.episodes.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.viknist.rickandmorty.core.di.ViewModelKey
import ru.viknist.rickandmorty.features.episodes.presentation.EpisodeDetailsViewModel
import ru.viknist.rickandmorty.features.episodes.presentation.EpisodeListViewModel

@Module
interface EpisodeViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(EpisodeListViewModel::class)
    fun bindEpisodeListViewModel(episodeListViewModel: EpisodeListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EpisodeDetailsViewModel::class)
    fun bindEpisodeDetailsViewModel(episodeDetailsViewModel: EpisodeDetailsViewModel): ViewModel
}