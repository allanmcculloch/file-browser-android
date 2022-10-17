package com.bereal.files.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bereal.files.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ImageDisplayDialog(image: ImageBitmap?, close: () -> Unit) {
    if (image != null) {
        Dialog(onDismissRequest = { close() }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
            Box(
                Modifier
                    .background(color = Color.White)
                    .fillMaxSize()
            ) {
                Icon(
                    Icons.Default.Close, contentDescription = stringResource(R.string.close),
                    modifier = Modifier
                        .clickable(onClick = { close() })
                        .align(alignment = Alignment.TopEnd)
                        .padding(16.dp)

                )
                Image(
                    bitmap = image, contentDescription = stringResource(R.string.picture),
                    modifier = Modifier
                        //.fillMaxSize(0.95f)
                        .align(Alignment.Center)
                        .padding(32.dp)
                )
            }
        }
    }
}