package com.bereal.files.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.bereal.files.ui.theme.BeRealFilesTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: FilesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BeRealFilesTheme {
                FilesComponent(
                    userName = viewModel.name,
                    isBrowseUpVisible = viewModel.isBrowseUpVisible,
                    fileItems = viewModel.fileItems,
                    image = viewModel.image,
                    isBusy = viewModel.isBusy,
                    navigateUp = { viewModel.navigateUp() },
                    browseDirectory = { viewModel.browseDirectory(it) },
                    closeImage = { viewModel.closeImage() },
                    createFolder = { viewModel.createFolder(it) },
                    deleteItem = { viewModel.deleteItem(it.id) },
                    uploadFile = { viewModel.uploadFile(it) }
                )
            }
        }
    }
}
