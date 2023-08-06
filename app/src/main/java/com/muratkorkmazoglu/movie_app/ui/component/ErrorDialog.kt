package com.muratkorkmazoglu.movie_app.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.muratkorkmazoglu.movie_app.ui.theme.MoviesColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDialog(
    title: String,
    description: String,
    onDismissRequest: (goBack: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onButtonClick: (goBack: Boolean) -> Unit,
) {
    AlertDialog(
        modifier = modifier.fillMaxWidth(0.80f),
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        onDismissRequest = {
            onDismissRequest(false)
        },
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large
        ) {
            Box(
                modifier = Modifier
                    .background(MoviesColors.BackgroundColor)
                    .padding(8.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        imageVector = Icons.Default.Warning,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { onButtonClick.invoke(false) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Tamam")
                    }

                }
            }
        }
    }
}