package com.muratkorkmazoglu.movie_app.core.data.repository

import com.muratkorkmazoglu.movie_app.core.data.remote.source.MoviesRemoteDataSource
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource
) : MoviesRepository