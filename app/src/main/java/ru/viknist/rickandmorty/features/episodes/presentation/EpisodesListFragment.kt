package ru.viknist.rickandmorty.features.episodes.presentation

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
import ru.viknist.rickandmorty.databinding.FragmentEpisodesListBinding
import ru.viknist.rickandmorty.features.episodes.di.EpisodeComponent
import ru.viknist.rickandmorty.features.episodes.models.EpisodeFilter
import ru.viknist.rickandmorty.features.episodes.presentation.recyclerView.EpisodeListAdapter

class EpisodesListFragment : BaseFragment<FragmentEpisodesListBinding, EpisodeListViewModel>(
    R.layout.fragment_episodes_list,
    EpisodeListViewModel::class.java
) {

    private var position: Int = 0
    private var filter = EpisodeFilter(
        "",
        ""
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = EpisodeListAdapter(requireContext()) { episodeEntity ->
            navigateEpisodeDetailsScreen(episodeEntity.id)
        }

        val layoutManager = GridLayoutManager(context, 2)
        binding.rvEpisodeList.layoutManager = layoutManager

        if (savedInstanceState != null) {
            filter = savedInstanceState.getSerializable(SAVED_FILTER_KEY) as EpisodeFilter
            position = savedInstanceState.getInt(SAVED_POSITION_KEY)
            layoutManager.scrollToPosition(position)
        }

        initPagination(layoutManager)
        initSwipeToRefresh()
        initCharacterListData()
        initSearchButton()
        initClearButtom()

        viewModel.episodeList.observe { result ->
            adapter.submitList(result)
            binding.rvEpisodeList.adapter = adapter
            layoutManager.scrollToPosition(position)

        }
    }

    private fun navigateEpisodeDetailsScreen(id: Int) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, EpisodeDetailsFragment.newInstance(id))
            .addToBackStack(EpisodesListFragment::class.getTag())
            .commit()
    }

    private fun initSearchButton() {
        binding.searchEpisodeImageButton.setOnClickListener {
            filter = applyFilter()
            position = 0
            viewModel.getEpisodeListNewFilter(filter)
        }
    }

    private fun initClearButtom() {
        binding.clearEpisodeFilterImageButton.setOnClickListener {
            clearFilter()
        }
    }

    private fun initSwipeToRefresh() {
        binding.swiperefreshEpisode.setOnRefreshListener {
            position = 0
            viewModel.getEpisodeListNewFilter(filter)
            binding.swiperefreshEpisode.isRefreshing = false
        }
    }

    private fun initCharacterListData() {
        viewModel.getEpisodeList(filter)
    }

    private fun initPagination(layoutManager: GridLayoutManager) {
        var lastOnScrollTime = 0L
        binding.rvEpisodeList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val currentTime = SystemClock.uptimeMillis()
                if (!recyclerView.canScrollVertically(1) && currentTime - lastOnScrollTime > Const.SCROLL_DELAY_MILLS) {
                    lastOnScrollTime = currentTime
                    position = layoutManager.findFirstVisibleItemPosition()
                    viewModel.getEpisodeList(filter)
                }
            }
        })
    }

    private fun clearFilter() {
        binding.episodeEpisodeFilterEditText.setText("")
        binding.nameEpisodeFilterEditText.setText("")
    }

    private fun applyFilter(): EpisodeFilter {
        return EpisodeFilter(
            binding.nameEpisodeFilterEditText.text.toString(),
            binding.episodeEpisodeFilterEditText.text.toString()
        )
    }

    override fun initDaggerComponent(dependenciesProvider: DependenciesProvider) {
        EpisodeComponent.init(dependenciesProvider, requireContext()).injectEpisodesList(this)
    }

    override fun initBinding(view: View) = FragmentEpisodesListBinding.bind(view)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val layoutManager = binding.rvEpisodeList.layoutManager as GridLayoutManager
        outState.putInt(SAVED_POSITION_KEY, layoutManager.findFirstVisibleItemPosition())
        outState.putSerializable(SAVED_FILTER_KEY, filter)
    }

    companion object {
        private const val SAVED_FILTER_KEY = "SAVED_FILTER_KEY"
        private const val SAVED_POSITION_KEY = "SAVED_POSITION_KEY"
        fun newInstance() = EpisodesListFragment()
    }
}