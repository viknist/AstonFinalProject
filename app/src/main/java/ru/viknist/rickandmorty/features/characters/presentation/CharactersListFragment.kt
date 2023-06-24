package ru.viknist.rickandmorty.features.characters.presentation

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.viknist.rickandmorty.R
import ru.viknist.rickandmorty.baseapp.getTag
import ru.viknist.rickandmorty.baseapp.presentation.Const.SCROLL_DELAY_MILLS
import ru.viknist.rickandmorty.core.DependenciesProvider
import ru.viknist.rickandmorty.core.base.BaseFragment
import ru.viknist.rickandmorty.databinding.FragmentCharactersListBinding
import ru.viknist.rickandmorty.features.characters.di.CharacterComponent
import ru.viknist.rickandmorty.features.characters.models.CharacterFilter
import ru.viknist.rickandmorty.features.characters.models.CharacterGender
import ru.viknist.rickandmorty.features.characters.models.CharacterStatus
import ru.viknist.rickandmorty.features.characters.presentation.recyclerView.CharacterListAdapter

class CharactersListFragment : BaseFragment<FragmentCharactersListBinding, CharacterListViewModel>(
    R.layout.fragment_characters_list,
    CharacterListViewModel::class.java
) {

    private var position: Int = 0
    private var filter = CharacterFilter(
        "",
        null,
        "",
        "",
        null
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CharacterListAdapter(requireContext()) { characterEntity ->
            navigateCharacterDetailsScreen(characterEntity.id)
        }
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCharactersList.layoutManager = layoutManager

        if (savedInstanceState != null) {
            filter = savedInstanceState.getSerializable(SAVED_FILTER_KEY) as CharacterFilter
            position = savedInstanceState.getInt(SAVED_POSITION_KEY)
            layoutManager.scrollToPosition(position)
        }

        initCharacterListData()

        viewModel.characterList.observe { result ->
            println(result.size)
            adapter.submitList(result)
            binding.rvCharactersList.adapter = adapter
            layoutManager.scrollToPosition(position)

        }

        initSearchButton()
        initClearButton()

        initPagination(layoutManager)
        initSwipeToRefresh()
    }

    private fun initClearButton() {
        binding.clearCharacterFilterImageButton.setOnClickListener {
            clearFilter()
        }
    }

    private fun initSearchButton() {
        binding.searchCharacterImageButton.setOnClickListener {
            filter = applyFilter()
            position = 0
            viewModel.getCharacterListNewFilter(filter)
        }
    }

    private fun initSwipeToRefresh() {
        binding.swiperefreshCharacter?.setOnRefreshListener {
            position = 0
            viewModel.getCharacterListNewFilter(filter)
            binding.swiperefreshCharacter!!.isRefreshing = false
        }
    }

    private fun initCharacterListData() {
        viewModel.getCharacterList(filter)
    }

    private fun initPagination(layoutManager: GridLayoutManager) {
        var lastOnScrollTime = 0L
        binding.rvCharactersList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val currentTime = SystemClock.uptimeMillis()
                if (!recyclerView.canScrollVertically(1) && currentTime - lastOnScrollTime > SCROLL_DELAY_MILLS) {
                    lastOnScrollTime = currentTime
                    position = layoutManager.findFirstVisibleItemPosition()
                    viewModel.getCharacterList(filter)
                }
            }
        })
    }

    private fun clearFilter() {
        binding.nameCharacterFilterEditText.setText("")
        binding.statusCharacterFilterSpinner.setSelection(0)
        binding.speciesCharacterFilterEditText.setText("")
        binding.typeCharacterFilterEditText.setText("")
        binding.genderCharacterFilterSpinner.setSelection(0)
    }

    private fun applyFilter(): CharacterFilter {
        return CharacterFilter(
            name = binding.nameCharacterFilterEditText.text.toString(),
            status = try {
                CharacterStatus.valueOf(
                    binding.statusCharacterFilterSpinner.selectedItem.toString()
                )
            } catch (exception: IllegalArgumentException) {
                null
            },
            species = binding.speciesCharacterFilterEditText.text.toString(),
            type = binding.typeCharacterFilterEditText.text.toString(),
            gender = try {
                CharacterGender.valueOf(
                    binding.genderCharacterFilterSpinner.selectedItem.toString()
                )
            } catch (exception: IllegalArgumentException) {
                null
            }
        )
    }

    override fun initDaggerComponent(dependenciesProvider: DependenciesProvider) {
        CharacterComponent.init(dependenciesProvider, requireContext()).injectCharactersList(this)
    }

    override fun initBinding(view: View) = FragmentCharactersListBinding.bind(view)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val layoutManager = binding.rvCharactersList.layoutManager as GridLayoutManager
        outState.putInt(SAVED_POSITION_KEY, layoutManager.findFirstVisibleItemPosition())
        outState.putSerializable(SAVED_FILTER_KEY, filter)
    }

    private fun navigateCharacterDetailsScreen(id: Int) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CharacterDetailsFragment.newInstance(id))
            .addToBackStack(CharactersListFragment::class.getTag())
            .commit()
    }

    companion object {
        private const val SAVED_FILTER_KEY = "SAVED_FILTER_KEY"
        private const val SAVED_POSITION_KEY = "SAVED_POSITION_KEY"
        fun newInstance() = CharactersListFragment()
    }
}