package ru.viknist.rickandmorty.features.locations.presentation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import ru.viknist.rickandmorty.core.base.BaseViewModel
import ru.viknist.rickandmorty.features.locations.domain.entity.LocationEntity
import ru.viknist.rickandmorty.features.locations.domain.usecase.GetFilteredLocationsDbUseCase
import ru.viknist.rickandmorty.features.locations.domain.usecase.GetFilteredLocationsWebUseCase
import ru.viknist.rickandmorty.features.locations.domain.usecase.SetLocationsDbUseCase
import ru.viknist.rickandmorty.features.locations.models.LocationFilter
import javax.inject.Inject


class LocationListViewModel @Inject constructor(
    private val getFilteredLocationWebUseCase: GetFilteredLocationsWebUseCase,
    private val getFilteredLocationDbUseCase: GetFilteredLocationsDbUseCase,
    private val setLocationsDbUseCase: SetLocationsDbUseCase
) : BaseViewModel() {

    private val _locationList = MutableStateFlow<List<LocationEntity>>(emptyList())
    val locationList = _locationList.filterNotNull()

    private val _onError = Channel<Unit>()
    val onError: Flow<Unit> = _onError.receiveAsFlow()

    private var currentPage: Int = 1

    fun getLocationList(filter: LocationFilter) {
        simpleLaunch(
            block = {
                getFilteredLocationWebUseCase.invoke(filter, currentPage)
            },
            onSuccess = { resultWeb ->
                simpleLaunch(
                    block = {
                        setLocationsDbUseCase.invoke(resultWeb)
                    },
                    onSuccess = {},
                    onFailure = { throwableDb ->
                        _onError.trySend(Unit)
                    }
                )
                _locationList.tryEmit(_locationList.value.plus(resultWeb.locationList))
                currentPage++
            },
            onFailure = { throwableWeb ->
                _onError.trySend(Unit)
                simpleLaunch(
                    block = {
                        getFilteredLocationDbUseCase(filter)
                    },
                    onSuccess = { resultDb ->
                        _locationList.tryEmit(resultDb.locationList)
                    },
                    onFailure = { throwableDb ->
                        println(throwableDb.toString())
                        _onError.trySend(Unit)
                    }
                )
            }
        )
    }

    fun getLocationListNewFilter(filter: LocationFilter) {
        currentPage = 1
        _locationList.value = emptyList()
        getLocationList(filter)
    }
}