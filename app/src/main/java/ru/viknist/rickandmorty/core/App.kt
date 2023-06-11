package ru.viknist.rickandmorty.core

import ru.viknist.rickandmorty.baseapp.di.AppComponent

interface App {

    fun appComponent(): AppComponent
}