package com.bereal.files.repository

import com.bereal.files.api.FileService
import com.bereal.files.domain.model.User
import com.bereal.files.domain.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface UserRepository {
    suspend fun getCurrentUser(): User
}

class UserRepositoryImpl @Inject constructor(private val fileService: FileService) : UserRepository {
    override suspend fun getCurrentUser() = withContext(Dispatchers.IO) {
        fileService.getCurrentUser().toDomain()
    }
}
