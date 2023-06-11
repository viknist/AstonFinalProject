package ru.viknist.rickandmorty.baseapp.domain.entity

data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
