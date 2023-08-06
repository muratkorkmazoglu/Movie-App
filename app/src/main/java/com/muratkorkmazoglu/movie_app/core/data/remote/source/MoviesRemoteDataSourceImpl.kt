package com.muratkorkmazoglu.movie_app.core.data.remote.source

import com.muratkorkmazoglu.movie_app.core.data.remote.api.MoviesService
import javax.inject.Inject


class MoviesRemoteDataSourceImpl @Inject constructor(
    private val moviesService: MoviesService,
) : MoviesRemoteDataSource
