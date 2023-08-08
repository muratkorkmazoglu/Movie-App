package com.muratkorkmazoglu.movie_app.core.data.repository

import androidx.paging.PagingData
import com.muratkorkmazoglu.movie_app.core.data.model.Movie
import com.muratkorkmazoglu.movie_app.core.data.model.MovieDetailsResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getPopularMovies(): Flow<PagingData<Movie>>
    fun getTopRatedMovies(): Flow<PagingData<Movie>>
    fun getUpcomingMovies(): Flow<PagingData<Movie>>
    suspend fun getMovieDetails(id: Int): Result<MovieDetailsResponse>

}