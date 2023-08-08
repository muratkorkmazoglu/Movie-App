package com.muratkorkmazoglu.movie_app.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.muratkorkmazoglu.movie_app.core.data.model.Movie
import com.muratkorkmazoglu.movie_app.core.data.model.MovieDetailsResponse
import com.muratkorkmazoglu.movie_app.core.data.pagingsource.PopularMoviesSource
import com.muratkorkmazoglu.movie_app.core.data.pagingsource.TopRatedMoviesSource
import com.muratkorkmazoglu.movie_app.core.data.pagingsource.UpcomingMoviesSource
import com.muratkorkmazoglu.movie_app.core.data.remote.api.MoviesService
import com.muratkorkmazoglu.movie_app.core.data.remote.getBodyOrThrowError
import com.muratkorkmazoglu.movie_app.core.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImp(
    private val moviesService: MoviesService
) : RemoteDataSource {

    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                PopularMoviesSource(moviesService)
            }
        ).flow
    }

    override fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                TopRatedMoviesSource(moviesService = moviesService)
            }
        ).flow
    }

    override fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                UpcomingMoviesSource(moviesService)
            }
        ).flow
    }

    override suspend fun getMovieDetails(id: Int): Result<MovieDetailsResponse> {
        return kotlin.runCatching {
            moviesService.getMovieDetails(id).getBodyOrThrowError()
        }
    }
}
