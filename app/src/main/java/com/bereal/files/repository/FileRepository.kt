package com.bereal.files.repository

import com.bereal.files.api.FileService
import com.bereal.files.api.model.CreateFolderRequest
import com.bereal.files.domain.model.FileItem
import com.bereal.files.domain.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import okhttp3.ResponseBody
import javax.inject.Inject

interface FileRepository {
    suspend fun getFileItems(id: String): List<FileItem>
    suspend fun getFileData(id: String): ResponseBody
    suspend fun uploadFile(parentFolderId: String, filename: String, requestBody: RequestBody): Boolean
    suspend fun createFolder(parentFolderId: String, name: String): Boolean
    suspend fun deleteItem(id: String): Boolean
}

class FileRepositoryImpl @Inject constructor(private val fileService: FileService) : FileRepository {
    override suspend fun getFileItems(id: String) = withContext(Dispatchers.IO) {
        fileService.getItem(id).map { it.toDomain() }
    }

    override suspend fun getFileData(id: String) = withContext(Dispatchers.IO) {
        fileService.getItemData(id)
    }

    override suspend fun createFolder(parentFolderId: String, name: String) = withContext(Dispatchers.IO) {
        val result = fileService.createFolder(parentFolderId, CreateFolderRequest(name))
        result.isSuccessful
    }

    override suspend fun uploadFile(parentFolderId: String, filename: String, requestBody: RequestBody) = withContext(Dispatchers.IO) {
        val contentDisposition = "attachment;filename*=utf-8''$filename"
        try {
            val result = fileService.uploadFile(parentFolderId, contentDisposition, requestBody)
            result.isSuccessful
        } catch (throwable: Throwable) {
            false
        }
    }

    override suspend fun deleteItem(id: String): Boolean = withContext(Dispatchers.IO) {
        val result = fileService.deleteItem(id)
        result.isSuccessful
    }
}