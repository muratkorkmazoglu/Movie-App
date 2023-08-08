package com.muratkorkmazoglu.movie_app.feature.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.muratkorkmazoglu.movie_app.arch.BaseViewModel
import com.muratkorkmazoglu.movie_app.arch.IViewState
import com.muratkorkmazoglu.movie_app.core.common.Resource
import com.muratkorkmazoglu.movie_app.core.common.asResource
import com.muratkorkmazoglu.movie_app.core.data.model.Movie
import com.muratkorkmazoglu.movie_app.core.domain.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<MovieDetailViewState>() {
    override fun createInitialState(): MovieDetailViewState = MovieDetailViewState()

    private val detailArgs = MovieDetailArgs(savedStateHandle)

    init {
        getMovie(detailArgs.id)
    }

    private fun getMovie(movieId: Int) {
        viewModelScope.launch {
            useCases.getMovieDetailUseCase.invoke(movieId).asResource().onEach {
                when (it) {
                    is Resource.Loading -> {
                        setState { copy(loading = true) }
                    }

                    is Resource.Success -> {
                        setState { copy(loading = false) }
                        Log.d("RESPONSEEE", it.data.originalTitle.toString())
                    }

                    is Resource.Error -> {
                        setState {
                            copy(
                                loading = false,
                            )
                        }
                    }
                }
            }.launchIn(this)
        }
    }


}

data class MovieDetailViewState(
    val loading: Boolean = false,
    val popularMovies: Flow<PagingData<Movie>> = emptyFlow(),

    ) : IViewState
