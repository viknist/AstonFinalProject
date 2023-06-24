package ru.viknist.rickandmorty.features.characters.domain.usecase

import ru.viknist.rickandmorty.features.characters.data.dto.CharactersResultResponse
import ru.viknist.rickandmorty.features.characters.data.repository.CharacterRepository
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterData
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterEntity
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterLocation
import ru.viknist.rickandmorty.baseapp.domain.entity.PageInfo
import ru.viknist.rickandmorty.baseapp.getIntFromUrl
import ru.viknist.rickandmorty.features.characters.models.CharacterFilter
import ru.viknist.rickandmorty.features.characters.models.CharacterGender
import ru.viknist.rickandmorty.features.characters.models.CharacterStatus
import javax.inject.Inject

class GetFilteredCharactersWebUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(filter: CharacterFilter, page: Int) : CharacterData{
      return map(characterRepository.getCharacterListWeb(filter, page))
    }

    private fun map(characterResponse: CharactersResultResponse): CharacterData{

        return CharacterData(
            PageInfo(
                characterResponse.info.count,
                characterResponse.info.pages,
                characterResponse.info.next,
                characterResponse.info.prev
            ),
            characterResponse.results.map {
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
        )
    }
}