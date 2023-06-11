package ru.viknist.rickandmorty.features.characters.domain.usecase

import ru.viknist.rickandmorty.baseapp.getIntFromUrl
import ru.viknist.rickandmorty.features.characters.data.dto.CharacterResponse
import ru.viknist.rickandmorty.features.characters.data.repository.CharacterRepository
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterEntity
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterLocation
import ru.viknist.rickandmorty.features.characters.models.CharacterGender
import ru.viknist.rickandmorty.features.characters.models.CharacterStatus
import javax.inject.Inject

class GetCharacterDetailsWebUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): CharacterEntity {
        return map(characterRepository.getCharacterByIdWeb(id))
    }

    private fun map(character: CharacterResponse): CharacterEntity {
        return CharacterEntity(
            character.id,
            character.name,
            CharacterStatus.valueOf(character.status.uppercase()),
            character.species,
            character.type,
            CharacterGender.valueOf(character.gender.uppercase()),
            CharacterLocation(
                character.origin.url.getIntFromUrl(),
                character.origin.name
            ),
            CharacterLocation(
                character.location.url.getIntFromUrl(),
                character.location.name
            ),
            character.image,
            character.episode.map { episode -> episode.getIntFromUrl() },
            character.url,
            character.created
        )
    }

}