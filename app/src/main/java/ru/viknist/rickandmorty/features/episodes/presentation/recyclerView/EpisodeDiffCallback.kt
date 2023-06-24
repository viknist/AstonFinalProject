package ru.viknist.rickandmorty.features.episodes.presentation.recyclerView

import androidx.recyclerview.widget.DiffUtil
import ru.viknist.rickandmorty.features.episodes.domain.entity.EpisodeEntity

object EpisodeDiffCallback : DiffUtil.ItemCallback<EpisodeEntity>() {

    override fun areItemsTheSame(oldItem: EpisodeEntity, newItem: EpisodeEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: EpisodeEntity, newItem: EpisodeEntity): Boolean {
        return oldItem == newItem
    }
}