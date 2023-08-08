package com.muratkorkmazoglu.movie_app.core.domain

import com.muratkorkmazoglu.movie_app.core.data.model.MovieDetailsResponse
import com.muratkorkmazoglu.movie_app.core.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val moviesRepository: MovieRepository,
) {
    operator fun invoke(id: Int): Flow<MovieDetailsResponse> {
        return flow {
            val result = moviesRepository.getMoviesDetails(id)
            (result.getOrNull() ?: result.getOrThrow()).also {
                emit(it)
            }
        }
    }
}