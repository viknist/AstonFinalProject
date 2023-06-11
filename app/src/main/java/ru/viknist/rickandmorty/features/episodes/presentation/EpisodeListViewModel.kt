package ru.viknist.rickandmorty.features.episodes.presentation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import ru.viknist.rickandmorty.core.base.BaseViewModel
import ru.viknist.rickandmorty.features.episodes.domain.entity.EpisodeEntity
import ru.viknist.rickandmorty.features.episodes.domain.usecase.GetFilteredEpisodesDbUseCase
import ru.viknist.rickandmorty.features.episodes.domain.usecase.GetFilteredEpisodesWebUseCase
import ru.viknist.rickandmorty.features.episodes.domain.usecase.SetEpisodesDbUseCase
import ru.viknist.rickandmorty.features.episodes.models.EpisodeFilter
import javax.inject.Inject

class EpisodeListViewModel @Inject constructor(
    private val getFilteredEpisodesWebUseCase: GetFilteredEpisodesWebUseCase,
    private val getFilteredEpisodesDbUseCase: GetFilteredEpisodesDbUseCase,
    private val setEpisodesDbUseCase: SetEpisodesDbUseCase
) : BaseViewModel() {

    private val _episodeList = MutableStateFlow<List<EpisodeEntity>>(emptyList())
    val episodeList = _episodeList.filterNotNull()

    private val _onError = Channel<Unit>()
    val onError: Flow<Unit> = _onError.receiveAsFlow()

    private var currentPage: Int = 1

    fun getEpisodeList(filter: EpisodeFilter) {
        simpleLaunch(
            block = {
                getFilteredEpisodesWebUseCase.invoke(filter, currentPage)
            },
            onSuccess = { resultWeb ->
                simpleLaunch(
                    block = {
                        setEpisodesDbUseCase.invoke(resultWeb)
                    },
                    onSuccess = {},
                    onFailure = { throwableDb ->
                        _onError.trySend(Unit)
                    }
                )
                _episodeList.tryEmit(_episodeList.value.plus(resultWeb.episodeList))
                currentPage++
            },
            onFailure = { throwableWeb ->
                _onError.trySend(Unit)
                simpleLaunch(
                    block = {
                        getFilteredEpisodesDbUseCase(filter)
                    },
                    onSuccess = { resultDb ->
                        _episodeList.tryEmit(resultDb.episodeList)
                    },
                    onFailure = { throwableDb ->
                        _onError.trySend(Unit)
                    }
                )
            }
        )
    }

    fun getEpisodeListNewFilter(filter: EpisodeFilter) {
        currentPage = 1
        _episodeList.value = emptyList()
        getEpisodeList(filter)
    }
}