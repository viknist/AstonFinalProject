package ru.viknist.rickandmorty.features.characters.domain.usecase

import ru.viknist.rickandmorty.baseapp.getIntFromUrl
import ru.viknist.rickandmorty.features.characters.data.dto.CharacterResponse
import ru.viknist.rickandmorty.features.characters.data.repository.CharacterRepository
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterEntity
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterLocation
import ru.viknist.rickandmorty.features.characters.models.CharacterGender
import ru.viknist.rickandmorty.features.characters.models.CharacterStatus
import javax.inject.Inject

class GetCharacterListByIdListWebUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(idList: List<Int>): List<CharacterEntity> {
        return map(characterRepository.getCharactersByListIdWeb(idList))
    }

    private fun map(list: List<CharacterResponse>): List<CharacterEntity> {
        return list.map {
            CharacterEntity(
                it.id,
                it.name,
                CharacterStatus.valueOf(it.status.uppercase()),
                it.species,
                it.type,
                CharacterGender.valueOf(it.gender.uppercase()),
                CharacterLocation(
                    it.origin.url.getIntFromUrl(),
                    it.origin.name
                ),
                CharacterLocation(
                    it.location.url.getIntFromUrl(),
                    it.location.name
                ),
                it.image,
                it.episode.map { episode -> episode.getIntFromUrl() },
                it.url,
                it.created
            )
        }
    }
}