package com.muratkorkmazoglu.movie_app.core.data.repository

import androidx.paging.PagingData
import com.muratkorkmazoglu.movie_app.core.data.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remote: RemoteDataSource,
) {
    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return remote.getPopularMovies()
    }

    fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return remote.getTopRatedMovies()
    }

    fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        return remote.getUpcomingMovies()
    }
}