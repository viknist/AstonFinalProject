package ru.viknist.rickandmorty.features.locations.presentation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import ru.viknist.rickandmorty.R
import ru.viknist.rickandmorty.baseapp.getTag
import ru.viknist.rickandmorty.core.DependenciesProvider
import ru.viknist.rickandmorty.core.base.BaseFragment
import ru.viknist.rickandmorty.databinding.FragmentLocationDetailsBinding
import ru.viknist.rickandmorty.features.characters.presentation.CharacterDetailsFragment
import ru.viknist.rickandmorty.features.characters.presentation.recyclerView.CharacterListAdapter
import ru.viknist.rickandmorty.features.locations.di.LocationComponent

class LocationDetailsFragment :
    BaseFragment<FragmentLocationDetailsBinding, LocationDetailsViewModel>(
        R.layout.fragment_location_details,
        LocationDetailsViewModel::class.java
    ) {

    private var position: Int = 0
    private var idLocation = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idLocation = requireArguments().getInt(ID_LOCATION)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CharacterListAdapter(requireContext()) { character ->
            navigateCharacterDetailsScreen(character.id)
        }
        val layoutManager = GridLayoutManager(context, 1)
        binding.rvCharactersLocationDetails.layoutManager = layoutManager

        if (savedInstanceState != null) {
            idLocation = savedInstanceState.getInt(ID_LOCATION)
            position = savedInstanceState.getInt(SAVED_POSITION_KEY)
            layoutManager.scrollToPosition(position)
        }

        viewModel.getLocationInfo(idLocation)

        viewModel.locationInfo.observe { result ->
            binding.nameLocationDetails.text = result.name
            binding.typeLocationDetails.text = result.type
            binding.dimensionLocationDetails.text = result.dimension
            viewModel.getCharactersList(result.residents)
        }

        viewModel.characterList.observe { result ->
            adapter.submitList(result)
            binding.rvCharactersLocationDetails.adapter = adapter

        }

        initSwipeToRefresh()
    }

    private fun initSwipeToRefresh() {
        binding.swiperefreshLocationDetails.setOnRefreshListener {
            position = 0
            viewModel.getLocationInfo(idLocation)
            binding.swiperefreshLocationDetails.isRefreshing = false
        }
    }

    override fun initBinding(view: View) = FragmentLocationDetailsBinding.bind(view)

    override fun initDaggerComponent(dependenciesProvider: DependenciesProvider) {
        LocationComponent.init(dependenciesProvider, requireContext()).injectLocationDetails(this)
    }

    private fun navigateCharacterDetailsScreen(id: Int) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CharacterDetailsFragment.newInstance(id))
            .addToBackStack(LocationDetailsFragment::class.getTag())
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val layoutManager = binding.rvCharactersLocationDetails.layoutManager as GridLayoutManager
        outState.putInt(SAVED_POSITION_KEY, layoutManager.findFirstVisibleItemPosition())
        outState.putInt(ID_LOCATION, idLocation)
    }

    companion object {
        private const val SAVED_POSITION_KEY = "SAVED_POSITION_KEY"
        private const val ID_LOCATION = "ID_LOCATION"
        fun newInstance(idLocation: Int) = LocationDetailsFragment().apply {
            val bundle = Bundle()
            bundle.putInt(ID_LOCATION, idLocation)
            this.arguments = bundle
        }
    }
}