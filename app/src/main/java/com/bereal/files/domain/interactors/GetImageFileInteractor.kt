package com.bereal.files.domain.interactors

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.bereal.files.repository.FileRepository
import javax.inject.Inject

class GetImageFileInteractor @Inject constructor(private val fileRepository: FileRepository) {
    suspend operator fun invoke(fileId: String): ImageBitmap {
        val file = fileRepository.getFileData(fileId)
        val bitmap = BitmapFactory.decodeByteArray(file.bytes(), 0, file.contentLength().toInt())
        return bitmap.asImageBitmap()
    }
}