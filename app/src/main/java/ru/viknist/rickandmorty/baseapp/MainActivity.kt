package ru.viknist.rickandmorty.baseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.viknist.rickandmorty.R
import ru.viknist.rickandmorty.features.characters.presentation.CharactersListFragment
import ru.viknist.rickandmorty.features.episodes.presentation.EpisodesListFragment
import ru.viknist.rickandmorty.features.locations.presentation.LocationsListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_RickAndMorty)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CharactersListFragment())
                .commit()
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.characters_nav -> navigateCharacterListScreen()
                R.id.locations_nav -> navigateLocationListScreen()
                R.id.episodes_nav -> navigateEpisodeListScreen()
                else -> {
                    false
                }
            }
        }
    }

    private fun navigateCharacterListScreen(): Boolean {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                CharactersListFragment(),
                CharactersListFragment::class.getTag()
            )
            .commit()
        return true
    }

    private fun navigateLocationListScreen(): Boolean {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                LocationsListFragment(),
                LocationsListFragment::class.getTag()
            )
            .commit()
        return true
    }

    private fun navigateEpisodeListScreen(): Boolean {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                EpisodesListFragment(),
                EpisodesListFragment::class.getTag()
            )
            .commit()
        return true
    }
}