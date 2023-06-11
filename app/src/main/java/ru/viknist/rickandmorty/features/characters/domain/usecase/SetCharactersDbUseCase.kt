package ru.viknist.rickandmorty.features.characters.domain.usecase

import ru.viknist.rickandmorty.features.characters.data.database.entity.CharacterEntityDb
import ru.viknist.rickandmorty.features.characters.data.database.entity.CharacterLocationEntity
import ru.viknist.rickandmorty.features.characters.data.repository.CharacterRepository
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterData
import javax.inject.Inject

class SetCharactersDbUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(data: CharacterData) {
        characterRepository.insertCharactersListDb(map(data))
    }

    private fun map(data: CharacterData): List<CharacterEntityDb> {
        return data.characterList.map {
            CharacterEntityDb(
                it.id,
                it.name,
                it.status.toString(),
                it.species,
                it.type,
                it.gender.toString(),
                CharacterLocationEntity(
                    it.origin.id,
                    it.origin.name
                ),
                CharacterLocationEntity(
                    it.location.id,
                    it.location.name
                ),
                it.image,
                it.episode.joinToString(" "),
                it.url,
                it.created
            )
        }
    }
}