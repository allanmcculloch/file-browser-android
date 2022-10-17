package com.bereal.files.api.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class FileItemDto(
    val contentType: String? = null,
    val id: String,
    val isDir: Boolean,
    val modificationDate: Instant,
    val name: String,
    val parentId: String,
    val size: Int? = null
)