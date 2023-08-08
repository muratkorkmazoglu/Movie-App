package com.muratkorkmazoglu.movie_app.feature.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.muratkorkmazoglu.movie_app.ui.theme.MoviesColors

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
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MoviesColors.PrimaryBlack)
            .statusBarsPadding()
    ) {
        Text(text = uiState.title, color = Color.White)
        Text(text = uiState.overview, color = Color.White)
    }
}
