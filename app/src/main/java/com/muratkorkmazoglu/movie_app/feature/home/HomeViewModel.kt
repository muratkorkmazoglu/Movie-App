package com.muratkorkmazoglu.movie_app.feature.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.muratkorkmazoglu.movie_app.arch.BaseViewModel
import com.muratkorkmazoglu.movie_app.arch.IViewState
import com.muratkorkmazoglu.movie_app.core.data.model.Movie
import com.muratkorkmazoglu.movie_app.core.domain.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: UseCases
) : BaseViewModel<HomeViewState>() {
    override fun createInitialState(): HomeViewState = HomeViewState()

    init {
        getUpComingMovies()
        getTopRatedMovies()
        getPopularMovies()
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch {
            setState {
                copy(
                    topRatedMovies = useCases.getTopRatedMoviesUseCase().cachedIn(viewModelScope)
                )
            }
        }
    }

    private fun getUpComingMovies() {
        viewModelScope.launch {
            setState {
                copy(
                    upcomingMovies = useCases.getUpcomingMoviesUseCase().cachedIn(viewModelScope)
                )
            }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            setState {
                copy(
                    popularMovies = useCases.getPopularMoviesUseCase().cachedIn(viewModelScope)
                )
            }
        }
    }

    fun onMovieClicked(movieId: Int?) {
        movieId?.let { id ->
            setState { copy(navigateToDetail = triggered(id)) }
        }
    }

    fun onConsumeNavigateToDetailSingleEvent() {
        setState { copy(navigateToDetail = consumed()) }
    }
}

data class HomeViewState(
    val popularMovies: Flow<PagingData<Movie>> = emptyFlow(),
    val topRatedMovies: Flow<PagingData<Movie>> = emptyFlow(),
    val upcomingMovies: Flow<PagingData<Movie>> = emptyFlow(),
    val navigateToDetail: StateEventWithContent<Int> = consumed(),

    ) : IViewState
