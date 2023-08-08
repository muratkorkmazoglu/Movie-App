package com.muratkorkmazoglu.movie_app.feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import com.muratkorkmazoglu.movie_app.feature.detail.movieDetailScreen
import com.muratkorkmazoglu.movie_app.feature.detail.navigateToMovieDetail
import com.muratkorkmazoglu.movie_app.feature.home.homeScreen
import com.muratkorkmazoglu.movie_app.feature.home.navigateToHome
import com.muratkorkmazoglu.movie_app.feature.splash.splashRoute
import com.muratkorkmazoglu.movie_app.feature.splash.splashScreen
import com.muratkorkmazoglu.movie_app.feature.video.navigateToVideo
import com.muratkorkmazoglu.movie_app.feature.video.videoScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = splashRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        splashScreen(navigateToHome = {
            navController.navigateToHome(
                NavOptions.Builder().setPopUpTo(0, true).build()
            )
        })
        homeScreen(navigateToDetail = { id ->
            navController.navigateToMovieDetail(id, navOptions = NavOptions.Builder().build())
        })
        movieDetailScreen(navigateToBack = {
            navController.navigateUp()
        }, navigateToVideo = { title, overview ->
            navController.navigateToVideo(
                title,
                overview,
                navOptions = NavOptions.Builder().build()
            )
        })
        videoScreen(navigateToBack = {
            navController.navigateUp()
        })
    }
}