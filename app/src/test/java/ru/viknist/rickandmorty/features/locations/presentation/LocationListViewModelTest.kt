package ru.viknist.rickandmorty.features.locations.presentation

import io.kotest.assertions.timing.eventually
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import ru.viknist.rickandmorty.baseapp.domain.entity.PageInfo
import ru.viknist.rickandmorty.features.locations.domain.entity.LocationData
import ru.viknist.rickandmorty.features.locations.domain.usecase.GetFilteredLocationsDbUseCase
import ru.viknist.rickandmorty.features.locations.domain.usecase.GetFilteredLocationsWebUseCase
import ru.viknist.rickandmorty.features.locations.domain.usecase.SetLocationsDbUseCase
import ru.viknist.rickandmorty.features.locations.models.LocationFilter

class LocationListViewModelTest : BehaviorSpec({

    val getFilteredLocationsWebUseCase = mockk<GetFilteredLocationsWebUseCase>()
    val getFilteredLocationsDbUseCase =  mockk<GetFilteredLocationsDbUseCase>()
    val setLocationsDbUseCase = mockk<SetLocationsDbUseCase>()
    val locationFilter = LocationFilter("","","")

    val locationListViewModel = LocationListViewModel(
        getFilteredLocationsWebUseCase,
        getFilteredLocationsDbUseCase,
        setLocationsDbUseCase
    )

    Given("Check that when internet is not working db is used"){
        coEvery { getFilteredLocationsWebUseCase.invoke(any(), any()) } throws Exception()
        coEvery {
            getFilteredLocationsDbUseCase.invoke(any())
        } returns LocationData(
            PageInfo(
                1,
                1,
                null,
                null
            ),
            emptyList()
        )
        When("locationViewModel.getLocationList(filter)"){
            eventually{locationListViewModel.getLocationList(locationFilter)}
            Then("shoud call web then db"){
                coVerifySequence {
                    getFilteredLocationsWebUseCase.invoke(any(), any())
                    getFilteredLocationsDbUseCase.invoke(any())
                }
            }
        }
    }
})
