package ru.viknist.rickandmorty.features.characters.data.dto

data class ResultInfoModel(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String
)
