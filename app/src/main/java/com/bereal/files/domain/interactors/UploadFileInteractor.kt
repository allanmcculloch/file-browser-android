package com.bereal.files.domain.interactors

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.bereal.files.repository.FileRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class UploadFileInteractor @Inject constructor(private val fileRepository: FileRepository, @ApplicationContext private val context: Context) {
    suspend operator fun invoke(parentId: String, uri: Uri) = withContext(Dispatchers.IO) {
        val filename = uri.getName(context)
        val extension = filename.substringAfterLast('.', "")
        val inputStream = context.contentResolver.openInputStream(uri)
        val imageByteArray = inputStream?.readBytes() ?: return@withContext

        val requestBody = imageByteArray.toRequestBody("image/$extension".toMediaType(), 0, imageByteArray.size)
        fileRepository.uploadFile(parentId, filename, requestBody)
        inputStream.close()
    }
}

fun Uri.getName(context: Context): String {
    val returnCursor = context.contentResolver.query(this, null, null, null, null) ?: return ""
    val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val fileName = returnCursor.getString(nameIndex)
    returnCursor.close()
    return fileName
}