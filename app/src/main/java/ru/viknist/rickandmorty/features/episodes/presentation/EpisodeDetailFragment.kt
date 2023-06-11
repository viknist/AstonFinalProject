package ru.viknist.rickandmorty.features.episodes.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.viknist.rickandmorty.R
import ru.viknist.rickandmorty.features.characters.presentation.CharactersListFragment

class EpisodeDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_episode_detail, container, false)
    }

    companion object {
        fun newInstance() =  EpisodeDetailFragment()
    }
}