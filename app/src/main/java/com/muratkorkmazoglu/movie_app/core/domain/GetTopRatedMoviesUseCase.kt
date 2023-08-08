package com.muratkorkmazoglu.movie_app.core.domain


import androidx.paging.PagingData
import com.muratkorkmazoglu.movie_app.core.data.model.Movie
import com.muratkorkmazoglu.movie_app.core.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val moviesRepository: MovieRepository,
) {
    operator fun invoke(): Flow<PagingData<Movie>> {
        return moviesRepository.getTopRatedMovies()
    }
}