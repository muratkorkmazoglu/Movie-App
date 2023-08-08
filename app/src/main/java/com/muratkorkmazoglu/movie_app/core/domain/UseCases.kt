package com.muratkorkmazoglu.movie_app.core.domain


class UseCases(
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    val getMovieDetailUseCase: GetMovieDetailUseCase,
)