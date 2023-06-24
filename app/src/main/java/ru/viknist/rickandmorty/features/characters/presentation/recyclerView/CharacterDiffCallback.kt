package ru.viknist.rickandmorty.features.characters.presentation.recyclerView

import androidx.recyclerview.widget.DiffUtil
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterEntity

object CharacterDiffCallback : DiffUtil.ItemCallback<CharacterEntity>() {

    override fun areItemsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
        return oldItem == newItem
    }
}