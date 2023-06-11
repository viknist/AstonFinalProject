package ru.viknist.rickandmorty.baseapp

import android.app.Application
import ru.viknist.rickandmorty.baseapp.di.AppComponent
import ru.viknist.rickandmorty.core.App

class MainApp: Application(), App {

    private var appComponent: AppComponent? = null

    override fun appComponent(): AppComponent {
        return getAppComponent()
    }

    private fun getAppComponent(): AppComponent {
        if (appComponent == null){
            appComponent = AppComponent.init(applicationContext)
        }
        return appComponent!!
    }
}