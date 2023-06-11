package ru.viknist.rickandmorty.features.episodes.presentation.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.viknist.rickandmorty.R
import androidx.recyclerview.widget.ListAdapter
import ru.viknist.rickandmorty.features.episodes.domain.entity.EpisodeEntity

class EpisodeListAdapter(
    Context: Context,
    private val onClickAction: (EpisodeEntity) -> Unit
) : ListAdapter<EpisodeEntity, EpisodeViewHolder>(
    EpisodeDiffCallback
) {

    private val inflater: LayoutInflater = LayoutInflater.from(Context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(inflater.inflate(R.layout.episode_item, parent, false))
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = getItem(position)
        holder.bind(episode)
        holder.itemView.rootView.setOnClickListener { onClickAction(episode) }
    }
}