package ru.viknist.rickandmorty.baseapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.viknist.rickandmorty.core.DependenciesProvider
import ru.viknist.rickandmorty.core.NetworkProvider
import ru.viknist.rickandmorty.network.NetworkComponent

@Component(
    dependencies = [NetworkProvider::class]
)
interface AppComponent : DependenciesProvider {

    companion object {
        fun init(context: Context): AppComponent {
            val networkProvider = NetworkComponent.init()
            return DaggerAppComponent.factory()
                .create(
                    networkProvider = networkProvider,
                    context = context
                )
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            networkProvider: NetworkProvider,
            @BindsInstance context: Context
        ): AppComponent
    }
}