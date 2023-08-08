package com.muratkorkmazoglu.movie_app.feature.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.muratkorkmazoglu.movie_app.arch.BaseViewModel
import com.muratkorkmazoglu.movie_app.arch.IViewState
import com.muratkorkmazoglu.movie_app.core.data.model.Movie
import com.muratkorkmazoglu.movie_app.core.domain.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: UseCases
) : BaseViewModel<HomeViewState>() {
    override fun createInitialState(): HomeViewState = HomeViewState()

    private val _topRatedMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val topRatedMovies: State<Flow<PagingData<Movie>>> = _topRatedMovies

    private val _getPopularMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val popularMovies: State<Flow<PagingData<Movie>>> = _getPopularMovies

    private val _getUpcomingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val upcomingMovies: State<Flow<PagingData<Movie>>> = _getUpcomingMovies

    init {
        getUpComingMovies()
        getTopRatedMovies()
        getPopularMovies()
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch {
            _topRatedMovies.value = useCases.getTopRatedMoviesUseCase().cachedIn(viewModelScope)
        }
    }

    private fun getUpComingMovies() {
        viewModelScope.launch {
            _getUpcomingMovies.value = useCases.getUpcomingMoviesUseCase().cachedIn(viewModelScope)
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            _getPopularMovies.value = useCases.getPopularMoviesUseCase().cachedIn(viewModelScope)
        }
    }
}

data class HomeViewState(
    val loading: Boolean = false,
) : IViewState
