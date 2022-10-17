package com.bereal.files.domain.model

data class User(
    val firstName: String,
    val lastName: String,
    val rootItem: FileItem
)

fun com.bereal.files.api.model.UserDto.toDomain() = User(
    this.firstName,
    this.lastName,
    this.rootItem.toDomain()
)
