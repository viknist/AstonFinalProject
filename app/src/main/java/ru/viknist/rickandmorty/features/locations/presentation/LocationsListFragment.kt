package ru.viknist.rickandmorty.features.locations.presentation

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.viknist.rickandmorty.R
import ru.viknist.rickandmorty.baseapp.getTag
import ru.viknist.rickandmorty.baseapp.presentation.Const
import ru.viknist.rickandmorty.core.DependenciesProvider
import ru.viknist.rickandmorty.core.base.BaseFragment
import ru.viknist.rickandmorty.databinding.FragmentLocationsListBinding
import ru.viknist.rickandmorty.features.locations.di.LocationComponent
import ru.viknist.rickandmorty.features.locations.models.LocationFilter
import ru.viknist.rickandmorty.features.locations.presentation.recyclerView.LocationListAdapter

class LocationsListFragment : BaseFragment<FragmentLocationsListBinding, LocationListViewModel>(
    R.layout.fragment_locations_list,
    LocationListViewModel::class.java
) {

    private var position: Int = 0
    private var filter = LocationFilter(
        "",
        "",
        ""
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = LocationListAdapter(requireContext()) { location ->
            navigateLocationDetailsScreen(location.id)
        }

        val layoutManager = GridLayoutManager(context, 2)
        binding.rvLocationsList.layoutManager = layoutManager

        if (savedInstanceState != null) {
            filter = savedInstanceState.getSerializable(SAVED_FILTER_KEY) as LocationFilter
            position = savedInstanceState.getInt(SAVED_POSITION_KEY)
            layoutManager.scrollToPosition(position)
        }

        initPagination(layoutManager)
        initSwipeToRefresh()
        initCharacterListData()

        viewModel.locationList.observe { result ->
            adapter.submitList(result)
            binding.rvLocationsList.adapter = adapter
            layoutManager.scrollToPosition(position)

        }

        initSearchButton()
        initClearButton()
    }

    private fun navigateLocationDetailsScreen(id: Int) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LocationDetailsFragment.newInstance(id))
            .addToBackStack(LocationsListFragment::class.getTag())
            .commit()
    }

    private fun initSearchButton() {
        binding.searchLocationImageButton.setOnClickListener {
            filter = applyFilter()
            position = 0
            viewModel.getLocationListNewFilter(filter)
        }
    }

    private fun initClearButton() {
        binding.clearLocationFilterImageButton.setOnClickListener {
            clearFilter()
        }
    }

    private fun initSwipeToRefresh() {
        binding.swiperefreshLocation.setOnRefreshListener {
            position = 0
            viewModel.getLocationListNewFilter(filter)
            binding.swiperefreshLocation.isRefreshing = false
        }
    }

    private fun initCharacterListData() {
        viewModel.getLocationList(filter)
    }

    private fun initPagination(layoutManager: GridLayoutManager) {
        var lastOnScrollTime = 0L
        binding.rvLocationsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val currentTime = SystemClock.uptimeMillis()
                if (!recyclerView.canScrollVertically(1) && currentTime - lastOnScrollTime > Const.SCROLL_DELAY_MILLS) {
                    lastOnScrollTime = currentTime
                    position = layoutManager.findFirstVisibleItemPosition()
                    viewModel.getLocationList(filter)
                }
            }
        })
    }

    private fun clearFilter() {
        binding.nameLocationFilterEditText.setText("")
        binding.typeLocationFilterEditText.setText("")
        binding.dimensionLocationFilterEditText.setText("")
    }

    private fun applyFilter(): LocationFilter {
        return LocationFilter(
            binding.nameLocationFilterEditText.text.toString(),
            binding.typeLocationFilterEditText.text.toString(),
            binding.dimensionLocationFilterEditText.text.toString()
        )
    }

    override fun initDaggerComponent(dependenciesProvider: DependenciesProvider) {
        LocationComponent.init(dependenciesProvider, requireContext()).injectLocationsList(this)
    }

    override fun initBinding(view: View) = FragmentLocationsListBinding.bind(view)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val layoutManager = binding.rvLocationsList.layoutManager as GridLayoutManager
        outState.putInt(SAVED_POSITION_KEY, layoutManager.findFirstVisibleItemPosition())
        outState.putSerializable(SAVED_FILTER_KEY, filter)
    }

    companion object {
        const val SAVED_FILTER_KEY = "SAVED_FILTER_KEY"
        const val SAVED_POSITION_KEY = "SAVED_POSITION_KEY"
        fun newInstance() = LocationsListFragment()
    }
}