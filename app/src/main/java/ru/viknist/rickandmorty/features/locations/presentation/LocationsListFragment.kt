package ru.viknist.rickandmorty.features.locations.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.viknist.rickandmorty.R
import ru.viknist.rickandmorty.features.episodes.presentation.EpisodesListFragment

class LocationsListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_locations_list, container, false)
    }

    companion object {
        fun newInstance() =  LocationsListFragment()
    }
}