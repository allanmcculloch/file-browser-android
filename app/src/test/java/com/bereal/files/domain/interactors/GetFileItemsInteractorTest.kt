package com.bereal.files.domain.interactors

import com.bereal.files.domain.model.FileItem
import com.bereal.files.repository.FileRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Instant
import org.junit.Before
import org.junit.Test

internal class GetFileItemsInteractorTest {
    private lateinit var fileRepository: FileRepository

    @Before
    fun setup() {
        fileRepository = mockk()
    }

    @Test
    fun `GetFileItems displays directories then files`() = runBlocking {
        val folderId = "1234"
        coEvery { fileRepository.getFileItems(folderId) }.returns(
            listOf(
                FileItem(id = "1", isDir = false, name = "1", modificationDate = Instant.DISTANT_FUTURE, parentId = "0"),
                FileItem(id = "2", isDir = false, name = "1", modificationDate = Instant.DISTANT_FUTURE, parentId = "0"),
                FileItem(id = "3", isDir = true, name = "1", modificationDate = Instant.DISTANT_FUTURE, parentId = "0"),
                FileItem(id = "4", isDir = false, name = "1", modificationDate = Instant.DISTANT_FUTURE, parentId = "0"),
                FileItem(id = "5", isDir = true, name = "1", modificationDate = Instant.DISTANT_FUTURE, parentId = "0"),
                FileItem(id = "6", isDir = true, name = "1", modificationDate = Instant.DISTANT_FUTURE, parentId = "0"),
            )
        )

        val result = GetFileItemsInteractor(fileRepository).invoke(folderId)

        assertThat(result).isEqualTo(
            listOf(
                FileItem(id = "3", isDir = true, name = "1", modificationDate = Instant.DISTANT_FUTURE, parentId = "0"),
                FileItem(id = "5", isDir = true, name = "1", modificationDate = Instant.DISTANT_FUTURE, parentId = "0"),
                FileItem(id = "6", isDir = true, name = "1", modificationDate = Instant.DISTANT_FUTURE, parentId = "0"),
                FileItem(id = "1", isDir = false, name = "1", modificationDate = Instant.DISTANT_FUTURE, parentId = "0"),
                FileItem(id = "2", isDir = false, name = "1", modificationDate = Instant.DISTANT_FUTURE, parentId = "0"),
                FileItem(id = "4", isDir = false, name = "1", modificationDate = Instant.DISTANT_FUTURE, parentId = "0"),
            )
        )
    }
}