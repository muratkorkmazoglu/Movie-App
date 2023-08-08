@file:OptIn(ExperimentalCoilApi::class)

package com.muratkorkmazoglu.movie_app.feature.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.muratkorkmazoglu.movie_app.ui.component.TopAppBar
import com.muratkorkmazoglu.movie_app.ui.theme.MoviesColors

@Composable
fun MovieDetailRoute(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    navigateToBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    MovieDetailScreen(
        uiState = uiState,
        navigateToBack = navigateToBack
    )
}

@Composable
fun MovieDetailScreen(
    uiState: MovieDetailViewState,
    modifier: Modifier = Modifier,
    navigateToBack: () -> Unit
) {
    Scaffold(containerColor = MoviesColors.PrimaryBlack, topBar = {
        TopAppBar(title = "Movie Detail", hasBackButton = true, navigateToBack = navigateToBack)
    }) {
        Content(uiState, modifier = modifier.padding(it))
    }
}


@Composable
fun Content(
    uiState: MovieDetailViewState,
    modifier: Modifier = Modifier,
) {
    Column {
        Text(text = "Movie Detail", color = Color.White)
    }
}

