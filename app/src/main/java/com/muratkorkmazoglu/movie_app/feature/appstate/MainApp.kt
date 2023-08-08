@file:OptIn(ExperimentalComposeUiApi::class)

package com.muratkorkmazoglu.movie_app.feature.appstate

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muratkorkmazoglu.movie_app.R
import com.muratkorkmazoglu.movie_app.core.util.NetworkMonitor
import com.muratkorkmazoglu.movie_app.feature.navigation.MainNavHost

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(
    ExperimentalComposeUiApi::class
)
@Composable
fun MainApp(
    networkMonitor: NetworkMonitor,
    modifier: Modifier = Modifier,
    appState: MainAppState = rememberMainAppState(
        networkMonitor = networkMonitor,
    ),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

    val notConnectedMessage = stringResource(R.string.not_network_connected)
    LaunchedEffect(isOffline) {
        if (isOffline)
            snackbarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = SnackbarDuration.Indefinite
            )
    }
    SideEffect {
        appState.systemUiController.setStatusBarColor(
            color = Color.Transparent
        )
    }

    Scaffold(
        modifier = modifier.semantics {
            testTagsAsResourceId = true
        },
        backgroundColor = MaterialTheme.colors.background,
    ) {
        MainNavHost(
            navController = appState.navController,
        )
    }
}

