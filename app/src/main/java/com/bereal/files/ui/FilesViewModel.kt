package com.bereal.files.ui

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bereal.files.domain.interactors.*
import com.bereal.files.domain.model.FileItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FilesViewModel @Inject constructor(
    private val getUserInteractor: GetUserInteractor,
    private val getFileItemsInteractor: GetFileItemsInteractor,
    private val getImageFileInteractor: GetImageFileInteractor,
    private val createFolderInteractor: CreateFolderInteractor,
    private val deleteItemInteractor: DeleteItemInteractor,
    private val uploadFileInteractor: UploadFileInteractor
) : ViewModel() {
    var name by mutableStateOf("")
    var fileItems by mutableStateOf(emptyList<FileItem>())
    var isBrowseUpVisible by mutableStateOf(false)
    var image by mutableStateOf<ImageBitmap?>(null)
    var isBusy by mutableStateOf(false)

    private val directoryParents = Stack<String>()
    private var currentDirectoryId: String = ""

    init {
        viewModelScope.launch {
            isBusy = true
            val user = getUserInteractor()
            name = "${user.firstName} ${user.lastName}"
            currentDirectoryId = user.rootItem.id
            refreshFileList()
            isBusy = false
        }
    }

    fun browseDirectory(file: FileItem) {
        viewModelScope.launch {
            isBusy = true
            if (file.isDir) {
                fileItems = getFileItemsInteractor(file.id)
                directoryParents.push(file.parentId)
                currentDirectoryId = file.id
            } else {
                image = getImageFileInteractor(file.id)
            }

            isBrowseUpVisible = directoryParents.isNotEmpty()
            isBusy = false
        }
    }

    fun navigateUp() {
        viewModelScope.launch {
            isBusy = true
            if (directoryParents.isEmpty()) {
                return@launch
            }
            val parentId = directoryParents.pop()
            if (parentId != null) {
                fileItems = getFileItemsInteractor(parentId)
            }

            currentDirectoryId = parentId
            isBrowseUpVisible = directoryParents.isNotEmpty()
            isBusy = false
        }
    }

    fun closeImage() {
        image = null
    }

    fun uploadFile(uri: Uri) {
        viewModelScope.launch {
            isBusy = true
            uploadFileInteractor(currentDirectoryId, uri)
            refreshFileList()
            isBusy = false
        }
    }

    fun createFolder(folderName: String) {
        viewModelScope.launch {
            isBusy = true
            createFolderInteractor(currentDirectoryId, folderName)
            refreshFileList()
            isBusy = false
        }
    }

    fun deleteItem(id: String) {
        viewModelScope.launch {
            isBusy = true
            deleteItemInteractor(id)
            refreshFileList()
            isBusy = false
        }
    }

    private suspend fun refreshFileList() {
        fileItems = getFileItemsInteractor(currentDirectoryId)
    }
}
