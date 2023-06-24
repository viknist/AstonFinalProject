package ru.viknist.rickandmorty.features.characters.domain.entity

import ru.viknist.rickandmorty.baseapp.domain.entity.PageInfo

data class CharacterData(
    val info: PageInfo,
    val characterList: List<CharacterEntity>
)