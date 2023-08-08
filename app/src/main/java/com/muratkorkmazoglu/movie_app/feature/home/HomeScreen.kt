@file:OptIn(ExperimentalCoilApi::class)

package com.muratkorkmazoglu.movie_app.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.muratkorkmazoglu.movie_app.R
import com.muratkorkmazoglu.movie_app.core.data.model.Movie
import com.muratkorkmazoglu.movie_app.core.util.Constants.IMAGE_BASE_URL
import com.muratkorkmazoglu.movie_app.ui.component.ErrorDialog
import com.muratkorkmazoglu.movie_app.ui.component.TopAppBar
import com.muratkorkmazoglu.movie_app.ui.theme.MoviesColors
import de.palm.composestateevents.EventEffect
import retrofit2.HttpException
import java.io.IOException

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    EventEffect(
        event = uiState.navigateToDetail,
        onConsumed = viewModel::onConsumeNavigateToDetailSingleEvent
    ) {
        navigateToDetail(it)
    }

    HomeScreen(
        uiState = uiState,
        onMovieClicked = viewModel::onMovieClicked
    )
}

@Composable
fun HomeScreen(
    uiState: HomeViewState,
    modifier: Modifier = Modifier,
    onMovieClicked: (Int) -> Unit
) {
    Scaffold(containerColor = MoviesColors.PrimaryBlack, topBar = {
        TopAppBar(title = "Movies", navigateToBack = {})
    }) {
        Content(uiState, modifier = modifier.padding(it), onMovieClicked = onMovieClicked)
    }
}


@Composable
fun Content(
    uiState: HomeViewState,
    modifier: Modifier = Modifier,
    onMovieClicked: (Int) -> Unit
) {
    val topRatedMovies = uiState.topRatedMovies.collectAsLazyPagingItems()
    val popularMovies = uiState.popularMovies.collectAsLazyPagingItems()
    val upcomingMovies = uiState.upcomingMovies.collectAsLazyPagingItems()
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            HorizontalMovieList(
                topRatedMovies,
                title = stringResource(R.string.top_rated),
                onMovieClicked = onMovieClicked
            )
        }
        item {
            HorizontalMovieList(
                popularMovies,
                title = stringResource(R.string.popular),
                onMovieClicked = onMovieClicked
            )
        }
        item {
            HorizontalMovieList(
                upcomingMovies,
                title = stringResource(R.string.upcoming),
                onMovieClicked = onMovieClicked
            )
        }
    }
}


@ExperimentalCoilApi
@Composable
fun MovieItem(
    imageUrl: String?,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, color = Color.Blue),
        elevation = 5.dp
    ) {
        imageUrl?.let {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                contentScale = ContentScale.FillWidth,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun ContentLoadingProgressIndicator(modifier: Modifier = Modifier, isVisible: Boolean = false) {
    if (isVisible)
        CircularProgressIndicator(
            modifier = modifier,
            color = Color.Blue,
            strokeWidth = 2.dp
        )
}

@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        color = Color.White,
        fontSize = 18.sp,
        modifier = modifier.padding(vertical = 16.dp)
    )
}

@Composable
fun HorizontalMovieList(
    movieList: LazyPagingItems<Movie>,
    title: String,
    modifier: Modifier = Modifier,
    onMovieClicked: (Int) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        SectionTitle(title = title)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movieList) { movie ->
                    MovieItem(
                        modifier = Modifier
                            .height(220.dp)
                            .width(180.dp)
                            .clickable {
                                onMovieClicked.invoke(movie?.id!!)
                            },
                        imageUrl = "$IMAGE_BASE_URL/${movie?.posterPath}",
                    )
                }
                item {
                    ContentLoadingProgressIndicator(isVisible = movieList.loadState.append == LoadState.Loading)
                }
            }
            movieList.apply {
                loadState
                when (loadState.refresh) {
                    is LoadState.Loading -> {
                        ContentLoadingProgressIndicator(isVisible = true)
                    }

                    is LoadState.Error -> {
                        val description =
                            when ((movieList.loadState.refresh as LoadState.Error).error) {
                                is HttpException -> "Oops! Something Went Wrong"
                                is IOException -> "Couldn't Reach Server! Check Your Internet Connection"
                                else -> "Unknown Error"
                            }
                        ErrorDialog(
                            title = "Error",
                            description = description,
                            onDismissRequest = {},
                            onButtonClick = {}
                        )
                    }

                    else -> Unit
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}