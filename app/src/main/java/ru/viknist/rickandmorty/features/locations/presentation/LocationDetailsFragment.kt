package ru.viknist.rickandmorty.features.locations.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.viknist.rickandmorty.R

class LocationDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_location_details, container, false)
    }

    companion object {
        fun newInstance() =  LocationDetailsFragment()
    }
}