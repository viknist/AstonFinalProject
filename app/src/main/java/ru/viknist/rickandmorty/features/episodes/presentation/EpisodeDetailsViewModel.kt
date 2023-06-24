package ru.viknist.rickandmorty.features.episodes.presentation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import ru.viknist.rickandmorty.core.base.BaseViewModel
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterEntity
import ru.viknist.rickandmorty.features.characters.domain.usecase.GetCharacterListByIdListDbUseCase
import ru.viknist.rickandmorty.features.characters.domain.usecase.GetCharacterListByIdListWebUseCase
import ru.viknist.rickandmorty.features.episodes.domain.entity.EpisodeEntity
import ru.viknist.rickandmorty.features.episodes.domain.usecase.GetEpisodeDetailsDbUseCase
import ru.viknist.rickandmorty.features.episodes.domain.usecase.GetEpisodeDetailsWebUseCase
import javax.inject.Inject

class EpisodeDetailsViewModel @Inject constructor(
    private val getEpisodeDetailsWebUseCase: GetEpisodeDetailsWebUseCase,
    private val getEpisodeDetailsDbUseCase: GetEpisodeDetailsDbUseCase,
    private val getCharacterListByIdListWebUseCase: GetCharacterListByIdListWebUseCase,
    private val getCharacterListByIdListDbUseCase: GetCharacterListByIdListDbUseCase
) : BaseViewModel() {

    private val _episodeInfo = MutableStateFlow<EpisodeEntity?>(null)
    val episodeInfo = _episodeInfo.filterNotNull()

    private val _characterList = MutableStateFlow<List<CharacterEntity>>(emptyList())
    val characterList = _characterList.filterNotNull()

    private val _onError = Channel<Unit>()
    val onError: Flow<Unit> = _onError.receiveAsFlow()

    fun getEpisodeInfo(id: Int) {
        simpleLaunch(
            block = {
                getEpisodeDetailsWebUseCase.invoke(id)
            },
            onSuccess = { resultWeb ->
                _episodeInfo.tryEmit(resultWeb)
            },
            onFailure = {
                simpleLaunch(
                    block = {
                        getEpisodeDetailsDbUseCase.invoke(id)
                    },
                    onSuccess = { resultDb ->
                        _episodeInfo.tryEmit(resultDb)
                    },
                    onFailure = { throwableDb ->
                        _onError.trySend(Unit)
                    }
                )
            }
        )
    }

    fun getCharactersList(list: List<Int>) {
        simpleLaunch(
            block = {
                getCharacterListByIdListWebUseCase.invoke(list)
            },
            onSuccess = { resultWeb ->
                _characterList.tryEmit(resultWeb)
            },
            onFailure = {
                simpleLaunch(
                    block = {
                        getCharacterListByIdListDbUseCase.invoke(list)
                    },
                    onSuccess = { resultDb ->
                        _characterList.tryEmit(resultDb)
                    },
                    onFailure = { throwableDb ->
                        _onError.trySend(Unit)
                    }
                )
            }
        )
    }
}