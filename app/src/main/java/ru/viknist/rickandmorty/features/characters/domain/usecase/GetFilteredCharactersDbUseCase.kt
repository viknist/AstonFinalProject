package ru.viknist.rickandmorty.features.characters.domain.usecase

import ru.viknist.rickandmorty.features.characters.data.database.entity.CharacterEntityDb
import ru.viknist.rickandmorty.features.characters.data.repository.CharacterRepository
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterData
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterEntity
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterLocation
import ru.viknist.rickandmorty.baseapp.domain.entity.PageInfo
import ru.viknist.rickandmorty.features.characters.models.CharacterFilter
import ru.viknist.rickandmorty.features.characters.models.CharacterGender
import ru.viknist.rickandmorty.features.characters.models.CharacterStatus
import javax.inject.Inject

class GetFilteredCharactersDbUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(filter: CharacterFilter): CharacterData {
        return map(characterRepository.getCharactersListDb(filter))
    }

    private fun map(characters: List<CharacterEntityDb>): CharacterData {
        return CharacterData(
            PageInfo(
                characters.size,
                1,
                null,
                null
            ),
            characters.map {
                CharacterEntity(
                    it.id,
                    it.name,
                    CharacterStatus.valueOf(it.status.uppercase()),
                    it.species,
                    it.type,
                    CharacterGender.valueOf(it.gender.uppercase()),
                    CharacterLocation(
                        it.origin.id,
                        it.origin.name
                    ),
                    CharacterLocation(
                        it.location.id,
                        it.location.name
                    ),
                    it.image,
                    it.episode.split(" ").map { it.toInt() },
                    it.url,
                    it.created
                )
            }
        )
    }


}