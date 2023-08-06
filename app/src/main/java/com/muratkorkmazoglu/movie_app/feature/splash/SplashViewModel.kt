package com.muratkorkmazoglu.movie_app.feature.splash

import androidx.lifecycle.viewModelScope
import com.muratkorkmazoglu.movie_app.arch.BaseViewModel
import com.muratkorkmazoglu.movie_app.arch.IViewState
import com.muratkorkmazoglu.movie_app.core.util.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor
) : BaseViewModel<SplashViewState>() {

    override fun createInitialState(): SplashViewState = SplashViewState()

    init {
        checkConnection()
    }

    private fun checkConnection() {
        runBlocking {
            if (networkMonitor.isOnline.first().not())
                setState { copy(hasConnectionError = true) }
            else
                checkSession()
        }
    }

    private fun checkSession() {
        viewModelScope.launch {
            delay(1500)
            setState {
                copy(
                    navigateToHome = triggered,
                )
            }
        }
    }

    fun onConsumeNavigateToHomeSingleEvent() {
        setState { copy(navigateToHome = consumed) }
    }


    fun onRetryConnection() {
        setState { copy(hasConnectionError = false) }
        viewModelScope.launch {
            delay(500)
            checkConnection()
        }
    }
}

data class SplashViewState(
    val navigateToHome: StateEvent = consumed,
    val hasConnectionError: Boolean = false
) : IViewState
