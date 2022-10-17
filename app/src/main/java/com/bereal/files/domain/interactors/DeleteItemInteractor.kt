package com.bereal.files.domain.interactors

import com.bereal.files.repository.FileRepository
import javax.inject.Inject

class DeleteItemInteractor @Inject constructor(private val fileRepository: FileRepository) {
    suspend operator fun invoke(id: String) =
        fileRepository.deleteItem(id)
}