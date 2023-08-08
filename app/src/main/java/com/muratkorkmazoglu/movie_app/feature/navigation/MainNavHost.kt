package com.muratkorkmazoglu.movie_app.feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import com.muratkorkmazoglu.movie_app.feature.home.homeScreen
import com.muratkorkmazoglu.movie_app.feature.home.navigateToHome
import com.muratkorkmazoglu.movie_app.feature.splash.splashRoute
import com.muratkorkmazoglu.movie_app.feature.splash.splashScreen

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
        homeScreen()
    }
}