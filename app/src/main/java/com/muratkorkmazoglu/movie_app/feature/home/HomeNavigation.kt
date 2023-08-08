package com.muratkorkmazoglu.movie_app.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val homeRoute = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
) {
    composable(
        route = homeRoute,
    ) {
        HomeRoute(
        )
    }
}
