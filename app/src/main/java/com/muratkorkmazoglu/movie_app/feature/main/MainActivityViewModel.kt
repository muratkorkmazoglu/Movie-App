package com.muratkorkmazoglu.movie_app.feature.main

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> = mainUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MainActivityUiState.Loading
        )
}

private fun mainUiState(): Flow<MainActivityUiState> {
    return flow {
        emit(MainActivityUiState.Loading)
        delay(1000)
        emit(MainActivityUiState.Success(MainActivityViewState()))
    }
}

sealed interface MainActivityUiState {
    object Loading : MainActivityUiState
    data class Success(val viewState: MainActivityViewState) : MainActivityUiState
}


data class MainActivityViewState(
    val loading: Boolean = false,
    val shouldShowCamera: Boolean = false,
    val shouldShowPhoto: Boolean = false,
    val photoUri: Uri? = null
)
