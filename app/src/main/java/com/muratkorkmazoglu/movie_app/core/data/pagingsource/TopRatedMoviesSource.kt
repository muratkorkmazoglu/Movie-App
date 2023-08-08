package com.muratkorkmazoglu.movie_app.core.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.muratkorkmazoglu.movie_app.core.data.model.Movie
import com.muratkorkmazoglu.movie_app.core.data.remote.api.MoviesService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TopRatedMoviesSource @Inject constructor(
    private val moviesService: MoviesService
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val tvTopRated = moviesService.getTopRatedMovies(nextPage)
            LoadResult.Page(
                data = tvTopRated.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (tvTopRated.results.isEmpty()) null else tvTopRated.page?.plus(
                    1
                )
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}