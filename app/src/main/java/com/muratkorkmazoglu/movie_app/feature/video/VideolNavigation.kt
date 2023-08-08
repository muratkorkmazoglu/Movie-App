package com.muratkorkmazoglu.movie_app.feature.video

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val videoRoute = "video_route"
const val titleDataArg = "titleArg"
const val overviewDataArg = "overviewArg"

internal class VideoArgs(
    val title: String,
    val overview: String
) {
    constructor(
        savedStateHandle: SavedStateHandle
    ) : this(
        title = checkNotNull(savedStateHandle[titleDataArg]),
        overview = checkNotNull(savedStateHandle[overviewDataArg])
    )
}

fun NavController.navigateToVideo(title: String, overview: String, navOptions: NavOptions? = null) {
    val encodedOverview = Uri.encode(overview)
    this.navigate(videoRoute.plus("/$title/$encodedOverview"), navOptions)
}

fun NavGraphBuilder.videoScreen(
    navigateToBack: () -> Unit
) {
    composable(
        route = videoRoute.plus("/{$titleDataArg}/{$overviewDataArg}"),
        arguments = listOf(
            navArgument(titleDataArg) {
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument(overviewDataArg) {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    ) {
        VideoRoute(
            navigateToBack = navigateToBack
        )
    }
}
