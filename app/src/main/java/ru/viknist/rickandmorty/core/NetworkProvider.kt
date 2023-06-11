package ru.viknist.rickandmorty.core

import retrofit2.Retrofit

interface NetworkProvider {

    fun provideRetrofit(): Retrofit
}