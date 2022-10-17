package com.bereal.files.domain.model

import com.bereal.files.api.model.FileItemDto
import com.google.common.truth.Truth.assertThat
import kotlinx.datetime.Instant
import org.junit.Test

internal class FileItemTest {
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

        val fileItem = fileItemDto.toDomain()

        assertThat(fileItem.id).isEqualTo(fileItemDto.id)
        assertThat(fileItem.contentType).isEqualTo(fileItemDto.contentType)
        assertThat(fileItem.isDir).isEqualTo(fileItemDto.isDir)
        assertThat(fileItem.modificationDate).isEqualTo(fileItemDto.modificationDate)
        assertThat(fileItem.name).isEqualTo(fileItemDto.name)
        assertThat(fileItem.parentId).isEqualTo(fileItemDto.parentId)
        assertThat(fileItem.size).isEqualTo(fileItemDto.size)
    }
}