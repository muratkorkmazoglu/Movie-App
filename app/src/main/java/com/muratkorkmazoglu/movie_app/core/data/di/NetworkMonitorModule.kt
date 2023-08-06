package com.muratkorkmazoglu.movie_app.core.data.di

import com.muratkorkmazoglu.movie_app.core.util.ConnectivityManagerNetworkMonitor
import com.muratkorkmazoglu.movie_app.core.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkMonitorModule {
    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor
}