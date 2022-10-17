package com.bereal.files.domain.interactors

import com.bereal.files.repository.FileRepository
import javax.inject.Inject

class CreateFolderInteractor @Inject constructor(private val fileRepository: FileRepository) {
    suspend operator fun invoke(parentId: String, folderName: String) =
        fileRepository.createFolder(parentId, folderName)
}