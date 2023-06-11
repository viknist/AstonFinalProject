package ru.viknist.rickandmorty.features.characters.domain.usecase

import ru.viknist.rickandmorty.features.characters.data.database.entity.CharacterEntityDb
import ru.viknist.rickandmorty.features.characters.data.repository.CharacterRepository
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterEntity
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterLocation
import ru.viknist.rickandmorty.features.characters.models.CharacterGender
import ru.viknist.rickandmorty.features.characters.models.CharacterStatus
import javax.inject.Inject

class GetCharacterListByIdListDbUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(idList: List<Int>): List<CharacterEntity> {
        return map(characterRepository.getCharactersByListIdDb(idList))
    }

    private fun map(list: List<CharacterEntityDb>): List<CharacterEntity> {
        return list.map {
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
                it.episode.split(" ").map { episode -> episode.toInt() },
                it.url,
                it.created
            )
        }
    }
}