package com.bereal.files.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bereal.files.R
import com.bereal.files.domain.model.FileItem
import com.bereal.files.ui.theme.BeRealFilesTheme
import kotlinx.datetime.Instant

@Composable
@OptIn(ExperimentalComposeApi::class)
fun FilesComponent(
    userName: String,
    isBrowseUpVisible: Boolean,
    fileItems: List<FileItem>,
    image: ImageBitmap?,
    isBusy: Boolean,
    navigateUp: () -> Unit,
    browseDirectory: (fileItem: FileItem) -> Unit,
    closeImage: () -> Unit,
    createFolder: (name: String) -> Unit,
    deleteItem: (fileItem: FileItem) -> Unit,
    uploadFile: (imageUri: Uri) -> Unit
) {
    val openCreateFolderDialog = remember { mutableStateOf(false) }

    val pickPictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { imageUri ->
        if (imageUri != null) {
            uploadFile(imageUri)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        FloatingActionButton(
            modifier = Modifier
                .padding(all = 32.dp)
                .align(alignment = Alignment.BottomEnd),
            onClick = { pickPictureLauncher.launch("image/*") }) {
            Icon(painterResource(id = R.drawable.ic_baseline_upload_24), stringResource(id = R.string.upload))
        }

        Column(modifier = Modifier.padding(16.dp)) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(if (isBusy) 1.0f else 0.0f)
                    .padding(vertical = 8.dp)
            )
            Text(text = "Logged in as: $userName!", modifier = Modifier.padding(top = 8.dp, bottom = 32.dp))
            FileCommandButton(isNavigateUpVisible = isBrowseUpVisible,
                navigateUp = { navigateUp() },
                showCreateFolderDialog = { openCreateFolderDialog.value = true }
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            FileList(fileItems, fileClick = { browseDirectory(it) }, deleteItem = deleteItem)
        }

        ImageDisplayDialog(image, close = { closeImage() })
        CreateFolderDialog(openCreateFolderDialog.value, createFolder = {
            createFolder(it)
            openCreateFolderDialog.value = false
        }, close = { openCreateFolderDialog.value = false })
    }
}


@Composable
private fun FileCommandButton(isNavigateUpVisible: Boolean, navigateUp: () -> Unit, showCreateFolderDialog: () -> Unit) {
    Row {
        if (isNavigateUpVisible) {
            Button(onClick = { navigateUp() }, modifier = Modifier.padding(end = 16.dp)) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        }
        Button(
            onClick = { showCreateFolderDialog() }) {
            Icon(painterResource(id = R.drawable.ic_baseline_create_new_folder_24), stringResource(id = R.string.create_folder))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FileList(fileList: List<FileItem>, fileClick: (file: FileItem) -> Unit, deleteItem: (file: FileItem) -> Unit) {
    val showDelete = remember { mutableStateOf("") }
    LazyColumn(modifier = Modifier.padding(bottom = 64.dp)) {
        items(fileList) { file ->
            Row(
                modifier = Modifier
                    .combinedClickable(
                        onClick = {
                            showDelete.value = ""
                            fileClick(file)
                        },
                        onLongClick = {
                            showDelete.value = file.id
                        })
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                if (file.isDir) {
                    Icon(
                        painterResource(R.drawable.ic_baseline_folder_24), contentDescription = stringResource(R.string.directory), Modifier.height(48.dp)
                    )
                } else {
                    Icon(
                        painterResource(R.drawable.ic_baseline_file_present_24), contentDescription = stringResource(R.string.file), Modifier.height(48.dp)
                    )
                }
                Text(
                    file.name, modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .align(Alignment.CenterVertically)
                )

                if (showDelete.value == file.id) {
                    Button(
                        modifier = Modifier
                            .padding(4.dp),
                        colors = ButtonDefaults.buttonColors(Color.Red),
                        onClick = { deleteItem(file) }) {
                        Icon(
                            painterResource(R.drawable.ic_baseline_delete_forever_24),
                            stringResource(R.string.delete),
                            modifier = Modifier.background(color = Color.Red)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BeRealFilesTheme {
        FilesComponent(userName = "Allan McCulloch", isBrowseUpVisible = true, fileItems = listOf(
            FileItem(
                id = "", isDir = true, modificationDate = Instant.DISTANT_FUTURE, name = "Test Directory", parentId = ""
            ), FileItem(
                id = "", isDir = true, modificationDate = Instant.DISTANT_FUTURE, name = "Test Directory 2", parentId = ""
            ), FileItem(
                id = "", isDir = false, modificationDate = Instant.DISTANT_FUTURE, name = "Test file", parentId = ""
            )
        ), image = null, isBusy = false, navigateUp = { }, browseDirectory = { }, closeImage = { }, createFolder = { }, deleteItem = { }, uploadFile = { })
    }
}
