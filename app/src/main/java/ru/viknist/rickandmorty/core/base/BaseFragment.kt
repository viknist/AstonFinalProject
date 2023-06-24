package ru.viknist.rickandmorty.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.viknist.rickandmorty.core.App
import ru.viknist.rickandmorty.core.DependenciesProvider
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    @LayoutRes private val layoutRes: Int,
    private val viewModelClass: Class<VM>
) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = requireNotNull(_binding) { "Binding isn't init" }

    private var _viewModel: VM? = null
    protected val viewModel get() = requireNotNull(_viewModel) { "ViewModel isn't init" }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDaggerComponent((requireActivity().applicationContext as App).appComponent())
        _viewModel = ViewModelProvider(this, viewModelFactory)[viewModelClass]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutRes, container, false)
        _binding = initBinding(view)
        return binding.root
    }

    abstract fun initBinding(view: View): VB

//    abstract fun createFactory(): VMF

    fun <T> Flow<T>.observe(action: (T) -> Unit) {
        flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach(action)
            .launchIn(lifecycleScope)
    }

    abstract fun initDaggerComponent(dependenciesProvider: DependenciesProvider)
}