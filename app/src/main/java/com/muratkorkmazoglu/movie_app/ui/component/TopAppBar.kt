package com.muratkorkmazoglu.movie_app.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun TopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    hasBackButton: Boolean = false,
    navigateToBack: () -> Unit
) {
    Column(modifier = modifier.statusBarsPadding()) {
        TopAppBar(
            elevation = 4.dp,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (hasBackButton) {
                        IconButton(onClick = navigateToBack) {
                            Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Text(title, style = MaterialTheme.typography.bodyLarge, color = Color.White)
                }
            },
            backgroundColor = Color.Blue,

            )
    }
}