package ru.viknist.rickandmorty.network

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [NetworkModule::class]
)
interface NetworkComponent {

    companion object {
        fun init(context: Context): NetworkComponent {
            return DaggerNetworkComponent.factory()
                .create(context)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context
        ): NetworkComponent
    }
}