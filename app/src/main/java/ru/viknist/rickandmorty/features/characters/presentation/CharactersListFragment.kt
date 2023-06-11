package ru.viknist.rickandmorty.features.characters.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.viknist.rickandmorty.R

class CharactersListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_characters_list, container, false)
    }

    companion object {
        fun newInstance() =  CharactersListFragment()
    }
}