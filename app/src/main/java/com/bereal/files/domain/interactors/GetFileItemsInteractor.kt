package com.bereal.files.domain.interactors

import com.bereal.files.domain.model.FileItem
import com.bereal.files.repository.FileRepository
import javax.inject.Inject

class GetFileItemsInteractor @Inject constructor(private val fileRepository: FileRepository) {
    suspend operator fun invoke(folderId: String): List<FileItem> =
        fileRepository.getFileItems(folderId).sortedByDescending { f -> f.isDir }
}