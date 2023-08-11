package com.muratkorkmazoglu.movie_app.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.muratkorkmazoglu.movie_app.arch.BaseViewModel
import com.muratkorkmazoglu.movie_app.arch.IViewState
import com.muratkorkmazoglu.movie_app.core.common.Resource
import com.muratkorkmazoglu.movie_app.core.common.asResource
import com.muratkorkmazoglu.movie_app.core.data.model.Genres
import com.muratkorkmazoglu.movie_app.core.data.model.Movie
import com.muratkorkmazoglu.movie_app.core.domain.UseCases
import com.muratkorkmazoglu.movie_app.core.util.Constants.IMAGE_BASE_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
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
        detailArgs.id?.let {
            getMovie(it)
        }
    }

    fun getMovie(movieId: Int) {
        viewModelScope.launch {
            useCases.getMovieDetailUseCase.invoke(movieId).asResource().onEach {
                when (it) {
                    is Resource.Loading -> {
                        setState { copy(loading = true) }
                    }

                    is Resource.Success -> {
                        setState { copy(loading = false) }
                        it.data.let { response ->
                            setState {
                                copy(
                                    posterUrl = "${IMAGE_BASE_URL}/${response.posterPath}",
                                    releaseDate = response.releaseDate,
                                    overview = response.overview,
                                    title = response.originalTitle,
                                    genres = response.genres
                                )
                            }

                        }
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

    fun onPlayVideoClicked() {
        setState {
            copy(
                navigateToVideo = triggered(
                    Pair(
                        currentState.title,
                        currentState.overview
                    )
                )
            )
        }
    }

    fun onConsumeNavigateToVideoSingleEvent() {
        setState { copy(navigateToVideo = consumed()) }
    }

    fun isTablet(isTablet: Boolean) {
        setState { copy(isTablet = isTablet) }
    }
}

data class MovieDetailViewState(
    val loading: Boolean = false,
    val popularMovies: Flow<PagingData<Movie>> = emptyFlow(),
    val posterUrl: String? = "",
    val releaseDate: String? = "",
    val overview: String? = "",
    val title: String? = "",
    val genres: List<Genres>? = listOf(),
    val navigateToVideo: StateEventWithContent<Pair<String?, String?>> = consumed(),
    val isTablet: Boolean = false
) : IViewState
