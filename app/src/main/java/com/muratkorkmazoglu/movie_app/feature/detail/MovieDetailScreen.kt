package com.muratkorkmazoglu.movie_app.feature.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.muratkorkmazoglu.movie_app.R
import com.muratkorkmazoglu.movie_app.ui.component.LoadingBar
import com.muratkorkmazoglu.movie_app.ui.theme.MoviesColors
import de.palm.composestateevents.EventEffect

@Composable
fun MovieDetailRoute(
    navigateToBack: () -> Unit,
    viewModel: MovieDetailViewModel = hiltViewModel(),
    navigateToVideo: (String, String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    EventEffect(
        event = uiState.navigateToVideo,
        onConsumed = viewModel::onConsumeNavigateToVideoSingleEvent
    ) {
        navigateToVideo(it.first.orEmpty(), it.second.orEmpty())
    }

    MovieDetailScreen(
        uiState = uiState,
        navigateToBack = navigateToBack,
        onPlayVideoClicked = viewModel::onPlayVideoClicked
    )
}

@Composable
fun MovieDetailScreen(
    uiState: MovieDetailViewState,
    onPlayVideoClicked: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToBack: () -> Unit
) {
    Content(
        uiState,
        modifier = modifier,
        navigateToBack = navigateToBack,
        onPlayVideoClicked = onPlayVideoClicked
    )
}

@Composable
fun Content(
    uiState: MovieDetailViewState,
    onPlayVideoClicked: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToBack: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MoviesColors.PrimaryBlack)
    ) {
        LoadingBar(isDisplayed = uiState.loading)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(MoviesColors.PrimaryBlack),
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = uiState.posterUrl)
                        .apply(block = fun ImageRequest.Builder.() {
                            placeholder(R.drawable.ic_placeholder)
                            crossfade(true)
                        }).build()
                ),
                contentScale = ContentScale.FillHeight,
                contentDescription = "",
            )
            IconButton(
                onClick = navigateToBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 28.dp, start = 16.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
            }
            Text(
                text = uiState.releaseDate.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = uiState.title.orEmpty(),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = uiState.overview.orEmpty(),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(uiState.genres.orEmpty()) { genre ->
                GenreBadge(text = genre.name)
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        PlayButton(onPlayVideoClicked = onPlayVideoClicked)
    }
}

@Composable
fun GenreBadge(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .clip(
                RoundedCornerShape(12.dp)
            )
            .background(MoviesColors.BadgeColor)
            .padding(8.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun PlayButton(modifier: Modifier = Modifier, onPlayVideoClicked: () -> Unit) {
    Button(
        onClick = onPlayVideoClicked,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.White),
        colors = ButtonDefaults.buttonColors(containerColor = MoviesColors.PrimaryBlack),
        elevation = ButtonDefaults.buttonElevation(5.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(36.dp)
    ) {
        Text(text = "Play Video", color = Color.White)
        Spacer(modifier = Modifier.width(4.dp))
        Icon(Icons.Filled.PlayArrow, null, tint = Color.White)
    }
}