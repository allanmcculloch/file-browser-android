package com.bereal.files.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.bereal.files.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ExperimentalComposeApi
fun CreateFolderDialog(isVisible: Boolean, createFolder: (folderName: String) -> Unit, close: () -> Unit) {
    val textState = remember { mutableStateOf(TextFieldValue()) }

    if (isVisible) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                Button(onClick = {
                    createFolder(textState.value.text)
                    textState.value = TextFieldValue("")
                }, enabled = textState.value.text.isNotEmpty()) {
                    Text("Create Folder")
                }
            },
            dismissButton = {
                Button(onClick = {
                    textState.value = TextFieldValue("")
                    close()
                }) {
                    Text("Close")
                }
            },
            title = { Text(text = stringResource(R.string.create_folder)) },
            text = {
                TextField(value = textState.value, onValueChange = {
                    if (it.text.length <= 30)
                        textState.value = it
                })
            }
        )
    }
}