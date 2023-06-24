package ru.viknist.rickandmorty.features.characters.presentation.recyclerView

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import ru.viknist.rickandmorty.R
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterEntity

class CharacterViewHolder(itemView: View) : ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.nameCharacterItem)
    val image: ImageView = itemView.findViewById(R.id.imageCharacterItem)
    val species: TextView = itemView.findViewById(R.id.speciesCharacterItem)
    val status: TextView = itemView.findViewById(R.id.statusCharacterItem)
    val gender: TextView = itemView.findViewById(R.id.genderCharacterItem)
    lateinit var character: CharacterEntity

    fun bind(characterEntity: CharacterEntity) {
        character = characterEntity
        name.text = characterEntity.name
        species.text = characterEntity.species
        status.text = characterEntity.status.toString()
        gender.text = characterEntity.gender.toString()
        image.load(characterEntity.image)
    }
}