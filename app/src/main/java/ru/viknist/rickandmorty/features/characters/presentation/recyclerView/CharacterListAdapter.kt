package ru.viknist.rickandmorty.features.characters.presentation.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.viknist.rickandmorty.R
import ru.viknist.rickandmorty.features.characters.domain.entity.CharacterEntity

class CharacterListAdapter(
    Context: Context,
    private val onClickAction: (CharacterEntity) -> Unit
) : ListAdapter<CharacterEntity, CharacterViewHolder>(
    CharacterDiffCallback
) {

    private val inflater: LayoutInflater = LayoutInflater.from(Context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(inflater.inflate(R.layout.character_item, parent, false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
        holder.itemView.rootView.setOnClickListener { onClickAction(character) }
    }

}