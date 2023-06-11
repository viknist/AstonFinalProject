package ru.viknist.rickandmorty.features.characters.presentation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import ru.viknist.rickandmorty.core.base.BaseViewModel
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterEntity
import ru.viknist.rickandmorty.features.characters.domain.usecase.GetCharacterDetailsDbUseCase
import ru.viknist.rickandmorty.features.characters.domain.usecase.GetCharacterDetailsWebUseCase
import ru.viknist.rickandmorty.features.episodes.domain.entity.EpisodeEntity
import ru.viknist.rickandmorty.features.episodes.domain.usecase.GetEpisodeListByIdListDbUseCase
import ru.viknist.rickandmorty.features.episodes.domain.usecase.GetEpisodeListByIdListWebUseCase
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterDetailsWebUseCase: GetCharacterDetailsWebUseCase,
    private val getCharacterDetailsDbUseCase: GetCharacterDetailsDbUseCase,
    private val getEpisodeListByIdListWebUseCase: GetEpisodeListByIdListWebUseCase,
    private val getEpisodeListByIdListDbUseCase: GetEpisodeListByIdListDbUseCase
) : BaseViewModel() {

    private val _characterInfo = MutableStateFlow<CharacterEntity?>(null)
    val characterInfo = _characterInfo.filterNotNull()

    private val _onError = Channel<Unit>()
    val onError: Flow<Unit> = _onError.receiveAsFlow()

    private val _episodeList = MutableStateFlow<List<EpisodeEntity>>(emptyList())
    val episodeList = _episodeList.filterNotNull()

    fun getCharacterInfo(id: Int) {
        simpleLaunch(
            block = {
                getCharacterDetailsWebUseCase.invoke(id)
            },
            onSuccess = { resultWeb ->
                _characterInfo.tryEmit(resultWeb)
            },
            onFailure = {
                simpleLaunch(
                    block = {
                        getCharacterDetailsDbUseCase.invoke(id)
                    },
                    onSuccess = { resultDb ->
                        _characterInfo.tryEmit(resultDb)
                    },
                    onFailure = { throwableDb ->
                        _onError.trySend(Unit)
                    }
                )
            }
        )
    }

    fun getEpisodesList(list: List<Int>) {
        simpleLaunch(
            block = {
                getEpisodeListByIdListWebUseCase.invoke(list)
            },
            onSuccess = { resultWeb ->
                _episodeList.tryEmit(resultWeb)
            },
            onFailure = {
                simpleLaunch(
                    block = {
                        getEpisodeListByIdListDbUseCase.invoke(list)
                    },
                    onSuccess = { resultDb ->
                        _episodeList.tryEmit(resultDb)
                    },
                    onFailure = { throwableDb ->
                        _onError.trySend(Unit)
                    }
                )
            }
        )
    }

}