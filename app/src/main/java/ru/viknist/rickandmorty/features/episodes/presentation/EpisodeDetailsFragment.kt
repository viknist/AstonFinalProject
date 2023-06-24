package ru.viknist.rickandmorty.features.episodes.presentation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import ru.viknist.rickandmorty.R
import ru.viknist.rickandmorty.baseapp.getTag
import ru.viknist.rickandmorty.core.DependenciesProvider
import ru.viknist.rickandmorty.core.base.BaseFragment
import ru.viknist.rickandmorty.databinding.FragmentEpisodeDetailBinding
import ru.viknist.rickandmorty.features.characters.presentation.CharacterDetailsFragment
import ru.viknist.rickandmorty.features.characters.presentation.recyclerView.CharacterListAdapter
import ru.viknist.rickandmorty.features.episodes.di.EpisodeComponent

class EpisodeDetailsFragment : BaseFragment<FragmentEpisodeDetailBinding, EpisodeDetailsViewModel>(
    R.layout.fragment_episode_detail,
    EpisodeDetailsViewModel::class.java
) {

    private var position: Int = 0
    private var idEpisode = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idEpisode = requireArguments().getInt(ID_EPISODE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CharacterListAdapter(requireContext()) { character ->
            navigateCharacterDetailsScreen(character.id)
        }
        val layoutManager = GridLayoutManager(context, 1)
        binding.rvCharactersList.layoutManager = layoutManager

        if (savedInstanceState != null) {
            idEpisode = savedInstanceState.getInt(ID_EPISODE)
            position = savedInstanceState.getInt(SAVED_POSITION_KEY)
            layoutManager.scrollToPosition(position)
        }

        viewModel.getEpisodeInfo(idEpisode)

        viewModel.episodeInfo.observe { result ->
            binding.episodeNameDetails.text = result.name
            binding.numberEpisodeDetails.text = result.episode
            binding.dateEpisodeDetails.text = result.air_date
            viewModel.getCharactersList(result.characters)
        }

        viewModel.characterList.observe { result ->
            adapter.submitList(result)
            binding.rvCharactersList.adapter = adapter
        }

        initSwipeToRefresh()
    }

    private fun initSwipeToRefresh() {
        binding.swiperefreshEpisodeDetails.setOnRefreshListener {
            position = 0
            viewModel.getEpisodeInfo(idEpisode)
            binding.swiperefreshEpisodeDetails.isRefreshing = false
        }
    }

    override fun initBinding(view: View) = FragmentEpisodeDetailBinding.bind(view)

    override fun initDaggerComponent(dependenciesProvider: DependenciesProvider) {
        EpisodeComponent.init(dependenciesProvider, requireContext()).injectEpisodeDetails(this)
    }

    private fun navigateCharacterDetailsScreen(id: Int) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CharacterDetailsFragment.newInstance(id))
            .addToBackStack(EpisodeDetailsFragment::class.getTag())
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val layoutManager = binding.rvCharactersList.layoutManager as GridLayoutManager
        outState.putInt(SAVED_POSITION_KEY, layoutManager.findFirstVisibleItemPosition())
        outState.putInt(ID_EPISODE, idEpisode)
    }

    companion object {
        private const val SAVED_POSITION_KEY = "SAVED_POSITION_KEY"
        private const val ID_EPISODE = "ID_EPISODE"
        fun newInstance(idEpisode: Int) = EpisodeDetailsFragment().apply {
            val bundle = Bundle()
            bundle.putInt(ID_EPISODE, idEpisode)
            this.arguments = bundle
        }
    }
}