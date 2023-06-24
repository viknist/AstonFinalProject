package ru.viknist.rickandmorty.features.episodes.presentation.recyclerView

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.viknist.rickandmorty.R
import ru.viknist.rickandmorty.features.episodes.domain.entity.EpisodeEntity

class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.episodeNameList)
    val number: TextView = itemView.findViewById(R.id.numberEpisodeList)
    val release: TextView = itemView.findViewById(R.id.releaseDateEpisodeList)
    lateinit var episode: EpisodeEntity

    fun bind(episodeEntity: EpisodeEntity) {
        episode = episodeEntity
        name.text = episodeEntity.name
        number.text = episodeEntity.episode
        release.text = episodeEntity.air_date
    }
}