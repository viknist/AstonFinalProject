package ru.viknist.rickandmorty.features.characters.presentation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import ru.viknist.rickandmorty.R
import ru.viknist.rickandmorty.baseapp.getTag
import ru.viknist.rickandmorty.core.DependenciesProvider
import ru.viknist.rickandmorty.core.base.BaseFragment
import ru.viknist.rickandmorty.databinding.FragmentCharacterDetailsBinding
import ru.viknist.rickandmorty.features.characters.di.CharacterComponent
import ru.viknist.rickandmorty.features.episodes.presentation.EpisodeDetailsFragment
import ru.viknist.rickandmorty.features.episodes.presentation.recyclerView.EpisodeListAdapter
import ru.viknist.rickandmorty.features.locations.presentation.LocationDetailsFragment

class CharacterDetailsFragment :
    BaseFragment<FragmentCharacterDetailsBinding, CharacterDetailsViewModel>(
        R.layout.fragment_character_details,
        CharacterDetailsViewModel::class.java
    ) {


    private var position: Int = 0
    private var idCharacter = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idCharacter = requireArguments().getInt(ID_CHARACTER)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = EpisodeListAdapter(requireContext()) { episode ->
            navigateEpisodeDetailsScreen(episode.id)
        }
        val layoutManager = GridLayoutManager(context, 1)
        binding.rvEpisodesCharacterDetails.layoutManager = layoutManager

        if (savedInstanceState != null) {
            idCharacter = savedInstanceState.getInt(ID_CHARACTER)
            position = savedInstanceState.getInt(SAVED_POSITION_KEY)
            layoutManager.scrollToPosition(position)
        }

        viewModel.getCharacterInfo(idCharacter)

        viewModel.characterInfo.observe { result ->
            binding.nameCharacterDetails.text = result.name
            binding.imageCharacterDetails.load(result.image)
            binding.genderCharacterDetails.text = result.name
            binding.locationCharacterDetails.text = result.location.name
            binding.originCharacterDetails.text = result.origin.name
            binding.speciesCharacterDetails.text = result.species
            binding.statusCharacterDetails.text = result.status.toString()
            binding.typeCharacterDetails.text = result.type
            viewModel.getEpisodesList(result.episode)

            initOriginButton(result.origin.id)
            initLocationButton(result.location.id)

        }

        viewModel.episodeList.observe { result ->
            adapter.submitList(result)
            binding.rvEpisodesCharacterDetails.adapter = adapter
        }



        initSwipeToRefresh()
    }

    private fun initOriginButton(id: Int) {
        if (id != 0) {
            binding.originCharacterDetails.setOnClickListener {
                navigateLocationDetailsScreen(id)
            }
        }
    }

    private fun initLocationButton(id: Int) {
        if (id != 0) {
            binding.locationCharacterDetails.setOnClickListener {
                navigateLocationDetailsScreen(id)
            }
        }
    }

    private fun navigateLocationDetailsScreen(id: Int) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LocationDetailsFragment.newInstance(id))
            .addToBackStack(CharacterDetailsFragment::class.getTag())
            .commit()
    }

    private fun initSwipeToRefresh() {
        binding.swipeCharacterDetails.setOnRefreshListener {
            position = 0
            viewModel.getCharacterInfo(idCharacter)
            binding.swipeCharacterDetails.isRefreshing = false
        }
    }

    private fun navigateEpisodeDetailsScreen(id: Int) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, EpisodeDetailsFragment.newInstance(id))
            .addToBackStack(CharacterDetailsFragment::class.getTag())
            .commit()
    }

    override fun initBinding(view: View) = FragmentCharacterDetailsBinding.bind(view)

    override fun initDaggerComponent(dependenciesProvider: DependenciesProvider) {
        CharacterComponent.init(dependenciesProvider, requireContext()).injectCharacterDetails(this)
    }

    companion object {
        private const val SAVED_POSITION_KEY = "SAVED_POSITION_KEY"
        private const val ID_CHARACTER = "ID_CHARACTER"
        fun newInstance(idCharacter: Int) = CharacterDetailsFragment().apply {
            val bundle = Bundle()
            bundle.putInt(ID_CHARACTER, idCharacter)
            this.arguments = bundle
        }
    }
}