package com.muratkorkmazoglu.movie_app.core.data.remote.api

import com.muratkorkmazoglu.movie_app.BuildConfig.API_KEY
import com.muratkorkmazoglu.movie_app.core.data.model.MovieDiscoverResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface MoviesService {
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") api_key: String = API_KEY,
        @Query("language") language: String = "en-US",
    ): MovieDiscoverResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") api_key: String = API_KEY,
        @Query("language") language: String = "en-US",
    ): MovieDiscoverResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") api_key: String = API_KEY,
        @Query("language") language: String = "en-US",
    ): MovieDiscoverResponse

}