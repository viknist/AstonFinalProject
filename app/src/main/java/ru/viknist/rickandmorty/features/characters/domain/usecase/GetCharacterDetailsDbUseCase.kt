package ru.viknist.rickandmorty.features.characters.domain.usecase

import ru.viknist.rickandmorty.features.characters.data.database.entity.CharacterEntityDb
import ru.viknist.rickandmorty.features.characters.data.repository.CharacterRepository
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterEntity
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterLocation
import ru.viknist.rickandmorty.features.characters.models.CharacterGender
import ru.viknist.rickandmorty.features.characters.models.CharacterStatus
import javax.inject.Inject

class GetCharacterDetailsDbUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): CharacterEntity {
        return map(characterRepository.getCharacterByIdDb(id))
    }

    private fun map(character: CharacterEntityDb): CharacterEntity {
        return CharacterEntity(
            character.id,
            character.name,
            CharacterStatus.valueOf(character.status.uppercase()),
            character.species,
            character.type,
            CharacterGender.valueOf(character.gender.uppercase()),
            CharacterLocation(
                character.origin.id,
                character.origin.name
            ),
            CharacterLocation(
                character.location.id,
                character.location.name
            ),
            character.image,
            character.episode.split(" ").map { it.toInt() },
            character.url,
            character.created
        )
    }
}