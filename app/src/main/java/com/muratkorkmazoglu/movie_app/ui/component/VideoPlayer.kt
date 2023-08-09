package com.muratkorkmazoglu.movie_app.ui.component

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun VideoPlayer(mExoPlayer: ExoPlayer, modifier: Modifier = Modifier) {
    val activity = LocalContext.current as Activity
    var orientation by remember { mutableStateOf(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) }
    val systemUiController: SystemUiController = rememberSystemUiController()


    LaunchedEffect(key1 = orientation, block = {
        systemUiController.isSystemBarsVisible =
            orientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        activity.requestedOrientation = orientation
    })
    AndroidView(modifier = modifier
        .fillMaxWidth()
        .height(500.dp), factory = { context ->
        PlayerView(context).apply {
            player = mExoPlayer
            setFullscreenButtonClickListener {
                orientation = if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                } else
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
    })
}

