package com.muratkorkmazoglu.movie_app.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.muratkorkmazoglu.movie_app.ui.component.ErrorDialog
import com.muratkorkmazoglu.movie_app.ui.theme.MoviesColors
import de.palm.composestateevents.EventEffect

@Composable
fun SplashRoute(
    navigateToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()


    EventEffect(
        event = uiState.navigateToHome,
        onConsumed = viewModel::onConsumeNavigateToHomeSingleEvent,
        action = navigateToHome
    )

    SplashScreen(uiState,
        onCLickRetry = {
            viewModel.onRetryConnection()
        })
}

@Composable
fun SplashScreen(
    uiState: SplashViewState,
    onCLickRetry: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Content(modifier = modifier, onCLickRetry = onCLickRetry, uiState = uiState)

}

@Composable
private fun Content(
    uiState: SplashViewState,
    onCLickRetry: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MoviesColors.PrimaryBlack),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState.hasConnectionError)
            ErrorDialog(
                title = "Bağlantı Problemi",
                description = "İnternet bağlantınla ilgili bir sorun algıladık. Lütfen ayarlarını kontrol et.",
                onDismissRequest = {},
                onButtonClick = onCLickRetry
            )
        SplashLottieAnimation(alignment = Alignment.Center)
    }
}


@Composable
fun SplashLottieAnimation(
    alignment: Alignment,
    modifier: Modifier = Modifier,
    lottieModifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(com.muratkorkmazoglu.movie_app.R.raw.splash_lottie))
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = alignment,
    ) {
        Column {
            LottieAnimation(
                composition,
                modifier = lottieModifier
                    .fillMaxWidth(),
                restartOnPlay = true,
                alignment = Alignment.Center,
                iterations = LottieConstants.IterateForever,
            )
        }
    }
}
