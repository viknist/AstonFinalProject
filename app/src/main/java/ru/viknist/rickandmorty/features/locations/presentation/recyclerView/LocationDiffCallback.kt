package ru.viknist.rickandmorty.features.locations.presentation.recyclerView

import androidx.recyclerview.widget.DiffUtil
import ru.viknist.rickandmorty.features.locations.domain.entity.LocationEntity

object LocationDiffCallback : DiffUtil.ItemCallback<LocationEntity>() {

    override fun areItemsTheSame(oldItem: LocationEntity, newItem: LocationEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LocationEntity, newItem: LocationEntity): Boolean {
        return oldItem == newItem
    }
}