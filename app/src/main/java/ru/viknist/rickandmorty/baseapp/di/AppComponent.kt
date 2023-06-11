package ru.viknist.rickandmorty.baseapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component


@Component
interface AppComponent {

    companion object {
        fun init(context: Context): AppComponent {
            return DaggerAppComponent.factory()
                .create(context)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }
}