package ru.viknist.rickandmorty.features.characters.presentation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import ru.viknist.rickandmorty.core.base.BaseViewModel
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterEntity
import ru.viknist.rickandmorty.features.characters.domain.usecase.GetFilteredCharactersDbUseCase
import ru.viknist.rickandmorty.features.characters.domain.usecase.GetFilteredCharactersWebUseCase
import ru.viknist.rickandmorty.features.characters.domain.usecase.SetCharactersDbUseCase
import ru.viknist.rickandmorty.features.characters.models.CharacterFilter
import javax.inject.Inject

class CharacterListViewModel @Inject constructor(
    private val getFilteredCharactersWebUseCase: GetFilteredCharactersWebUseCase,
    private val getFilteredCharactersDbUseCase: GetFilteredCharactersDbUseCase,
    private val setCharactersDbUseCase: SetCharactersDbUseCase
) : BaseViewModel() {

    private val _characterList = MutableStateFlow<List<CharacterEntity>>(emptyList())
    val characterList = _characterList.filterNotNull()

    private val _onError = Channel<Unit>()
    val onError: Flow<Unit> = _onError.receiveAsFlow()

    private var currentPage: Int = 1

    fun getCharacterList(filter: CharacterFilter) {
        simpleLaunch(
            block = {
                getFilteredCharactersWebUseCase.invoke(filter, currentPage)
            },
            onSuccess = { resultWeb ->
                simpleLaunch(
                    block = {
                        setCharactersDbUseCase.invoke(resultWeb)
                    },
                    onSuccess = {},
                    onFailure = { throwableDb ->
                        _onError.trySend(Unit)
                    }
                )
                _characterList.tryEmit(_characterList.value.plus(resultWeb.characterList))
                currentPage++
            },
            onFailure = { throwableWeb ->
                _onError.trySend(Unit)
                simpleLaunch(
                    block = {
                        getFilteredCharactersDbUseCase(filter)
                    },
                    onSuccess = { resultDb ->
                        _characterList.tryEmit(resultDb.characterList)
                    },
                    onFailure = { throwableDb ->
                        _onError.trySend(Unit)
                    }
                )
            }
        )
    }

    fun getCharacterListNewFilter(filter: CharacterFilter) {
        currentPage = 1
        _characterList.value = emptyList()
        getCharacterList(filter)
    }
}