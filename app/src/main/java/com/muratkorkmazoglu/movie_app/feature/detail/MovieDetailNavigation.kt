package com.muratkorkmazoglu.movie_app.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val movieDetailRoute = "movieDetail_route"
const val idDataArg = "idArg"

internal class MovieDetailArgs(
    val id: Int
) {
    constructor(
        savedStateHandle: SavedStateHandle
    ) : this(
        id = checkNotNull(savedStateHandle[idDataArg]),
    )
}

fun NavController.navigateToMovieDetail(id: Int, navOptions: NavOptions? = null) {
    this.navigate(movieDetailRoute.plus("/$id"), navOptions)
}

fun NavGraphBuilder.movieDetailScreen(
    navigateToBack: () -> Unit
) {
    composable(
        route = movieDetailRoute.plus("/{$idDataArg}"),
        arguments = listOf(
            navArgument(idDataArg) {
                type = NavType.IntType
                defaultValue = 0
            }
        )
    ) {
        MovieDetailRoute(
            navigateToBack = navigateToBack
        )
    }
}
