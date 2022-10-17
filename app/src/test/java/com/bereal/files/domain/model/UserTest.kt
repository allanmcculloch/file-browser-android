package com.bereal.files.domain.model

import com.bereal.files.api.model.FileItemDto
import com.bereal.files.api.model.UserDto
import com.google.common.truth.Truth.assertThat
import kotlinx.datetime.Instant
import org.junit.Test

internal class UserTest {
    @Test
    fun `toDomain maps correctly`() {
        val fileItemDto = FileItemDto(
            contentType = "image/jpeg",
            id = "someid",
            isDir = true,
            modificationDate = Instant.DISTANT_FUTURE,
            name = "somename.jpg",
            parentId = "123",
            size = 12321
        )
        val userDto = UserDto(firstName = "John", lastName = "Smith", rootItem = fileItemDto)

        val user = userDto.toDomain()

        assertThat(user.firstName).isEqualTo(userDto.firstName)
        assertThat(user.lastName).isEqualTo(userDto.lastName)
        assertThat(user.rootItem.id).isEqualTo(userDto.rootItem.id)
    }
}