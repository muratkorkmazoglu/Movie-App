package com.muratkorkmazoglu.movie_app.feature.splash

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val splashRoute = "splash_route"

fun NavController.navigateToSplash(navOptions: NavOptions? = null) {
    this.navigate(splashRoute, navOptions)
}

fun NavGraphBuilder.splashScreen(
    navigateToHome: () -> Unit
) {
    composable(
        route = splashRoute,
    ) {
        SplashRoute(
            navigateToHome = navigateToHome
        )
    }
}
