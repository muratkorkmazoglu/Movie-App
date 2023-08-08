package com.muratkorkmazoglu.movie_app.feature.video

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.muratkorkmazoglu.movie_app.arch.BaseViewModel
import com.muratkorkmazoglu.movie_app.arch.IViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<VideoViewState>() {
    override fun createInitialState(): VideoViewState = VideoViewState()

    private val videoArgs = VideoArgs(savedStateHandle)

    init {
        setState { copy(title = videoArgs.title, overview = Uri.decode(videoArgs.overview)) }
    }


}

data class VideoViewState(
    val loading: Boolean = false,
    val title: String = "",
    val overview: String = ""
) : IViewState
