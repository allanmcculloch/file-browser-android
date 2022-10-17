package com.bereal.files.api.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val firstName: String,
    val lastName: String,
    val rootItem: FileItemDto
)