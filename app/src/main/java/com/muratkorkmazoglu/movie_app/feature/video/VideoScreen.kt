package com.muratkorkmazoglu.movie_app.feature.video

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.muratkorkmazoglu.movie_app.ui.component.VideoPlay

@Composable
fun VideoRoute(
    navigateToBack: () -> Unit,
    viewModel: VideoViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    VideoScreen(
        uiState = uiState,
        navigateToBack = navigateToBack,
    )
}

@Composable
fun VideoScreen(
    uiState: VideoViewState,
    modifier: Modifier = Modifier,
    navigateToBack: () -> Unit
) {
    Content(
        uiState,
        modifier = modifier,
        navigateToBack = navigateToBack
    )
}

@Composable
fun Content(
    uiState: VideoViewState,
    modifier: Modifier = Modifier,
    navigateToBack: () -> Unit
) {
    Column {
        VideoPlay(navigateToBack)
    }

}
