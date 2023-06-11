package ru.viknist.rickandmorty.network

import dagger.Component
import ru.viknist.rickandmorty.core.NetworkProvider

@Component(
    modules = [NetworkModule::class]
)
interface NetworkComponent : NetworkProvider {

    companion object {
        fun init(): NetworkComponent {
            return DaggerNetworkComponent.factory()
                .create()
        }
    }

    @Component.Factory
    interface Factory {

        fun create(): NetworkComponent
    }
}