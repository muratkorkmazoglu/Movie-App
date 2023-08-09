package com.muratkorkmazoglu.movie_app.feature.video

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.exoplayer.ExoPlayer
import com.muratkorkmazoglu.movie_app.ui.component.VideoPlayer
import com.muratkorkmazoglu.movie_app.ui.theme.MoviesColors
import com.muratkorkmazoglu.movie_app.util.DrmHelper

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

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun Content(
    uiState: VideoViewState,
    modifier: Modifier = Modifier,
    navigateToBack: () -> Unit
) {
    Column(modifier = modifier
        .fillMaxSize()
        .background(MoviesColors.PrimaryBlack)) {
        val mContext = LocalContext.current
        val mExoPlayer = remember(mContext) {
            ExoPlayer.Builder(mContext).build().apply {
                setMediaSource(DrmHelper().createMediaSource(context = mContext))
                playWhenReady = true
                prepare()
            }
        }
        BackHandler(true) {
            mExoPlayer.release()
            navigateToBack.invoke()
        }
        VideoPlayer(mExoPlayer)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = uiState.title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = uiState.overview,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}
