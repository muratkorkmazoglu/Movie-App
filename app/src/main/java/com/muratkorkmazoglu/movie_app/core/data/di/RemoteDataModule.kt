package com.muratkorkmazoglu.movie_app.core.data.di

import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.muratkorkmazoglu.movie_app.BuildConfig
import com.muratkorkmazoglu.movie_app.core.data.remote.api.MoviesService
import com.muratkorkmazoglu.movie_app.core.data.repository.MovieRepository
import com.muratkorkmazoglu.movie_app.core.data.repository.RemoteDataSource
import com.muratkorkmazoglu.movie_app.core.data.repository.RemoteDataSourceImp
import com.muratkorkmazoglu.movie_app.core.domain.GetMovieDetailUseCase
import com.muratkorkmazoglu.movie_app.core.domain.GetPopularMoviesUseCase
import com.muratkorkmazoglu.movie_app.core.domain.GetTopRatedMoviesUseCase
import com.muratkorkmazoglu.movie_app.core.domain.GetUpcomingMoviesUseCase
import com.muratkorkmazoglu.movie_app.core.domain.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        chuckInterceptor: ChuckerInterceptor,

        ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(chuckInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context,
        chuckerCollector: ChuckerCollector,
    ): ChuckerInterceptor {

        return ChuckerInterceptor.Builder(context).collector(chuckerCollector)
            .maxContentLength(250_000L)
            .redactHeaders("Content-Type", "application/json")
            .alwaysReadResponseBody(true).build()
    }

    @Singleton
    @Provides
    fun provideChuckerCollector(@ApplicationContext context: Context): ChuckerCollector =
        ChuckerCollector(
            context = context,
            // Toggles visibility of the push notification
            showNotification = true,
            // Allows to customize the retention period of collected data
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

    @Provides
    fun provideMoviesService(retrofit: Retrofit): MoviesService {
        return retrofit.create(MoviesService::class.java)
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }


    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient,
    ): ImageLoaderFactory {
        return ImageLoaderFactory {
            ImageLoader.Builder(context)
                .okHttpClient(okHttpClient)
                .build()
        }
    }

    @Provides
    @Singleton
    fun providesRemoteDataSource(
        moviesService: MoviesService,
    ): RemoteDataSource {
        return RemoteDataSourceImp(
            moviesService = moviesService
        )
    }

    @Provides
    @Singleton
    fun providesUseCases(repository: MovieRepository): UseCases {
        return UseCases(
            getPopularMoviesUseCase = GetPopularMoviesUseCase(repository),
            getTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(repository),
            getUpcomingMoviesUseCase = GetUpcomingMoviesUseCase(repository),
            getMovieDetailUseCase = GetMovieDetailUseCase(repository)
            )
    }
}
