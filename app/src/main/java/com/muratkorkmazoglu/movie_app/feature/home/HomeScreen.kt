package com.muratkorkmazoglu.movie_app.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import retrofit2.HttpException
import java.io.IOException

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val topRatedMovies = viewModel.topRatedMovies.value.collectAsLazyPagingItems()
    val popularMovies = viewModel.popularMovies.value.collectAsLazyPagingItems()
    val upcomingMovies = viewModel.upcomingMovies.value.collectAsLazyPagingItems()
    HomeScreen(
        uiState = uiState,
        topRatedMovies = topRatedMovies,
        popularMovies = popularMovies,
        upcomingMovies = upcomingMovies
    )
}

@Composable
fun HomeScreen(
    uiState: HomeViewState,
    topRatedMovies: LazyPagingItems<Movie>,
    popularMovies: LazyPagingItems<Movie>,
    upcomingMovies: LazyPagingItems<Movie>,
) {
    Content(topRatedMovies, popularMovies, upcomingMovies)

}


@Composable
fun Content(
    topRatedMovies: LazyPagingItems<Movie>,
    popularMovies: LazyPagingItems<Movie>,
    upcomingMovies: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.top_rated),
                color = Color.Black,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentAlignment = Alignment.Center
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    items(topRatedMovies) { film ->
                        HeroItem(
                            modifier = Modifier
                                .height(220.dp)
                                .width(200.dp)
                                .clickable {

                                },
                            imageUrl = "$IMAGE_BASE_URL/${film!!.posterPath}",
                        )
                    }

                    if (topRatedMovies.loadState.append == LoadState.Loading) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
                topRatedMovies.apply {
                    loadState
                    when (loadState.refresh) {
                        is LoadState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier,
                                color = Color.Red,
                                strokeWidth = 2.dp
                            )
                        }

                        is LoadState.Error -> {
                            val error = topRatedMovies.loadState.refresh as LoadState.Error
                            Text(
                                text = when (error.error) {
                                    is HttpException -> {
                                        "Oops! Something Went Wrong"
                                    }

                                    is IOException -> {
                                        "Couldn't Reach Server! Check Your Internet Connection"
                                    }

                                    else -> {
                                        "Unknown Error"
                                    }
                                },
                                textAlign = TextAlign.Center,
                                color = Color.Red
                            )
                        }

                        else -> {}
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text(
                text = stringResource(R.string.popular),
                color = Color.Black,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentAlignment = Alignment.Center
            ) {
                LazyRow {
                    items(popularMovies) { film ->
                        HeroItem(
                            modifier = Modifier
                                .height(200.dp)
                                .width(200.dp)
                                .clickable {
                                },
                            imageUrl = "$IMAGE_BASE_URL/${film!!.posterPath}",
                        )
                    }

                    if (popularMovies.loadState.append == LoadState.Loading) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
                popularMovies.apply {
                    loadState
                    when (loadState.refresh) {
                        is LoadState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier,
                                color = Color.Red,
                                strokeWidth = 2.dp
                            )
                        }

                        is LoadState.Error -> {
                            val error = popularMovies.loadState.refresh as LoadState.Error
                            Text(
                                text = when (error.error) {
                                    is HttpException -> {
                                        "Oops! Something Went Wrong"
                                    }

                                    is IOException -> {
                                        "Couldn't Reach Server! Check Your Internet Connection"
                                    }

                                    else -> {
                                        "Unknown Error"
                                    }
                                },
                                textAlign = TextAlign.Center,
                                color = Color.Red
                            )
                        }

                        else -> {}
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text(
                text = stringResource(R.string.upcoming),
                color = Color.Black,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentAlignment = Alignment.Center
            ) {
                LazyRow {

                    items(upcomingMovies) { film ->
                        HeroItem(
                            modifier = Modifier
                                .height(200.dp)
                                .width(200.dp)
                                .clickable {

                                },
                            imageUrl = "$IMAGE_BASE_URL/${film!!.posterPath}",
                        )
                    }

                    if (upcomingMovies.loadState.append == LoadState.Loading) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
                upcomingMovies.apply {
                    loadState
                    when (loadState.refresh) {
                        is LoadState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier,
                                color = Color.Red,
                                strokeWidth = 2.dp
                            )
                        }

                        is LoadState.Error -> {
                            val error = upcomingMovies.loadState.refresh as LoadState.Error
                            Text(
                                text = when (error.error) {
                                    is HttpException -> {
                                        "Oops! Something Went Wrong"
                                    }

                                    is IOException -> {
                                        "Couldn't Reach Server! Check Your Internet Connection"
                                    }

                                    else -> {
                                        "Unknown Error"
                                    }
                                },
                                textAlign = TextAlign.Center,
                                color = Color.Red
                            )
                        }

                        else -> {}
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@ExperimentalCoilApi
@Composable
fun HeroItem(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = imageUrl).apply(block = fun ImageRequest.Builder.() {
                    crossfade(true)
                }).build()
            ),
            contentScale = ContentScale.Crop,
            contentDescription = ""
        )
    }
}

