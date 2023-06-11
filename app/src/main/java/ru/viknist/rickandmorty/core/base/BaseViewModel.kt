package ru.viknist.rickandmorty.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        _errorState.tryEmit(throwable.localizedMessage)
    }

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.filterNotNull()

    protected fun <T> simpleLaunch(
        block: suspend () -> T,
        onSuccess: (T) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            simpleSubscribe(block, onSuccess, onFailure)
        }
    }
}

suspend fun <T> simpleSubscribe(
    block: suspend () -> T,
    onSuccess: (T) -> Unit,
    onFailure: (Throwable) -> Unit

) {
    runCatching {
        block()
    }
        .onSuccess(onSuccess)
        .onFailure(onFailure)
}