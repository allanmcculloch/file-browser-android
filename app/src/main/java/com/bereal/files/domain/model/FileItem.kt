package com.bereal.files.domain.model

import kotlinx.datetime.Instant

data class FileItem(
    val contentType: String? = null,
    val id: String,
    val isDir: Boolean,
    val modificationDate: Instant,
    val name: String,
    val parentId: String,
    val size: Int? = null
)

fun com.bereal.files.api.model.FileItemDto.toDomain() = FileItem(
    this.contentType,
    this.id,
    this.isDir,
    this.modificationDate,
    this.name,
    this.parentId,
    this.size
)
