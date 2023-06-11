package ru.viknist.rickandmorty.features.locations.presentation.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.viknist.rickandmorty.R
import ru.viknist.rickandmorty.features.locations.domain.entity.LocationEntity

class LocationListAdapter(
    Context: Context,
    private val onClickAction: (LocationEntity) -> Unit
) : ListAdapter<LocationEntity, LocationViewHolder>(
    LocationDiffCallback
) {

    private val inflater: LayoutInflater = LayoutInflater.from(Context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(inflater.inflate(R.layout.location_item, parent, false))
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = getItem(position)
        holder.bind(location)
        holder.itemView.rootView.setOnClickListener { onClickAction(location) }
    }
}