package ru.viknist.rickandmorty.features.locations.presentation.recyclerView

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.viknist.rickandmorty.R
import ru.viknist.rickandmorty.features.locations.domain.entity.LocationEntity

class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.nameLocationItem)
    val type: TextView = itemView.findViewById(R.id.typeLocationItem)
    val dimension: TextView = itemView.findViewById(R.id.dimensionLocationItem)
    lateinit var location: LocationEntity

    fun bind(locationEntity: LocationEntity) {
        location = locationEntity
        name.text = locationEntity.name
        type.text = locationEntity.type
        dimension.text = locationEntity.dimension
    }
}