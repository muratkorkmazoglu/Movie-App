package com.muratkorkmazoglu.movie_app.core.data.di

import com.muratkorkmazoglu.movie_app.core.data.remote.source.MoviesRemoteDataSource
import com.muratkorkmazoglu.movie_app.core.data.remote.source.MoviesRemoteDataSourceImpl
import com.muratkorkmazoglu.movie_app.core.data.repository.MoviesRepository
import com.muratkorkmazoglu.movie_app.core.data.repository.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
interface MoviesModule {

    @Binds
    fun bindMoviesDataSource(sourceImpl: MoviesRemoteDataSourceImpl): MoviesRemoteDataSource

    @Binds
    fun bindMoviesRepository(repositoryImpl: MoviesRepositoryImpl): MoviesRepository
}