package ru.viknist.rickandmorty.baseapp.data.dto

data class ResultInfoResponse(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String
)
