package ru.viknist.rickandmorty.features.locations.presentation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import ru.viknist.rickandmorty.core.base.BaseViewModel
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterEntity
import ru.viknist.rickandmorty.features.characters.domain.usecase.GetCharacterListByIdListDbUseCase
import ru.viknist.rickandmorty.features.characters.domain.usecase.GetCharacterListByIdListWebUseCase
import ru.viknist.rickandmorty.features.locations.domain.entity.LocationEntity
import ru.viknist.rickandmorty.features.locations.domain.usecase.GetLocationDetailsDbUseCase
import ru.viknist.rickandmorty.features.locations.domain.usecase.GetLocationDetailsWebUseCase
import javax.inject.Inject

class LocationDetailsViewModel @Inject constructor(
    private val getLocationDetailsWebUseCase: GetLocationDetailsWebUseCase,
    private val getLocationDetailsDbUseCase: GetLocationDetailsDbUseCase,
    private val getCharacterListByIdListWebUseCase: GetCharacterListByIdListWebUseCase,
    private val getCharacterListByIdListDbUseCase: GetCharacterListByIdListDbUseCase
) : BaseViewModel() {

    private val _locationInfo = MutableStateFlow<LocationEntity?>(null)
    val locationInfo = _locationInfo.filterNotNull()

    private val _characterList = MutableStateFlow<List<CharacterEntity>>(emptyList())
    val characterList = _characterList.filterNotNull()

    private val _onError = Channel<Unit>()
    val onError: Flow<Unit> = _onError.receiveAsFlow()

    fun getLocationInfo(id: Int) {
        simpleLaunch(
            block = {
                getLocationDetailsWebUseCase.invoke(id)
            },
            onSuccess = { resultWeb ->
                _locationInfo.tryEmit(resultWeb)
            },
            onFailure = {
                simpleLaunch(
                    block = {
                        getLocationDetailsDbUseCase.invoke(id)
                    },
                    onSuccess = { resultDb ->
                        _locationInfo.tryEmit(resultDb)
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