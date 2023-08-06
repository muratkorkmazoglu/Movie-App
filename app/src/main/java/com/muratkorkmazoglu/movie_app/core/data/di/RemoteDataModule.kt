package com.muratkorkmazoglu.movie_app.core.data.di

import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.muratkorkmazoglu.movie_app.BuildConfig
import com.muratkorkmazoglu.movie_app.core.data.local.datastore.DataStoreManager
import com.muratkorkmazoglu.movie_app.core.data.remote.api.MoviesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
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
        authInterceptor: AuthInterceptor,

        ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(chuckInterceptor)
            .addInterceptor(authInterceptor)
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

    @Singleton
    @Provides
    fun provideAuthInterceptor(dataStoreManager: DataStoreManager): AuthInterceptor =
        AuthInterceptor(dataStoreManager = dataStoreManager)

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
}

class AuthInterceptor @Inject constructor(private val dataStoreManager: DataStoreManager) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = ""
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader("Content-Type", "application/json")
        if (token.isEmpty().not()) requestBuilder.addHeader("Authorization", "Bearer $token")
        return chain.proceed(requestBuilder.build())
    }
}