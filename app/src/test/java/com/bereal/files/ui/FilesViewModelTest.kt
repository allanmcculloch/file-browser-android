package com.bereal.files.ui

import android.net.Uri
import com.bereal.files.CoroutinesTestRule
import com.bereal.files.domain.interactors.*
import com.bereal.files.domain.model.FileItem
import com.bereal.files.domain.model.User
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.datetime.Instant
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class FilesViewModelTest {
    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private lateinit var filesViewModel: FilesViewModel
    private val getUserInteractor: GetUserInteractor = mockk()
    private val getFileItemsInteractor: GetFileItemsInteractor = mockk()
    private val getImageFileInteractor: GetImageFileInteractor = mockk()
    private val createFolderInteractor: CreateFolderInteractor = mockk(relaxed = true)
    private val deleteItemInteractor: DeleteItemInteractor = mockk(relaxed = true)
    private val uploadFileInteractor: UploadFileInteractor = mockk(relaxed = true)

    @Before
    fun setUp() {
        coEvery { getUserInteractor() }.returns(user)
        coEvery { getFileItemsInteractor(user.rootItem.id) }.returns(listOf(rootItem.copy(id = "anotheritem")))
        createViewModel()
    }

    @Test
    fun `name is set to the current user name`() {
        assertThat(filesViewModel.name).isEqualTo("Allan McCulloch")
    }

    @Test
    fun `createFolder executes then refreshes file items`() {
        filesViewModel.createFolder("newfoldername")

        coVerify { createFolderInteractor.invoke(rootItem.id, "newfoldername") }
        coVerify { getFileItemsInteractor.invoke(rootItem.id) }
    }

    @Test
    fun `up button is not visible in root directory`() {
        assertThat(filesViewModel.isBrowseUpVisible).isEqualTo(false)
    }

    @Test
    fun `up button is visible when not in root directory`() {
        coEvery { getFileItemsInteractor.invoke(testDirectory.id) }.returns(emptyList())
        filesViewModel.browseDirectory(testDirectory)

        assertThat(filesViewModel.isBrowseUpVisible).isEqualTo(true)

        filesViewModel.navigateUp()
        assertThat(filesViewModel.isBrowseUpVisible).isEqualTo(false)
    }

    @Test
    fun `uploadFile executes then refreshes file items`() {
        val mockUri: Uri = mockk()

        filesViewModel.uploadFile(mockUri)

        coVerify { uploadFileInteractor.invoke(rootItem.id, mockUri) }
        coVerify { getFileItemsInteractor.invoke(rootItem.id) }
    }

    @Test
    fun `deleteItem executes then refreshes file items`() {
        filesViewModel.deleteItem("someitemid")

        coVerify { deleteItemInteractor.invoke("someitemid") }
        coVerify { getFileItemsInteractor.invoke(rootItem.id) }
    }

    private fun createViewModel() {
        filesViewModel = FilesViewModel(
            getUserInteractor = getUserInteractor,
            getFileItemsInteractor = getFileItemsInteractor,
            getImageFileInteractor = getImageFileInteractor,
            createFolderInteractor = createFolderInteractor,
            deleteItemInteractor = deleteItemInteractor,
            uploadFileInteractor = uploadFileInteractor,
        )
    }

    private val rootItem = FileItem(
        id = "rootItemId",
        isDir = true,
        modificationDate = Instant.DISTANT_FUTURE,
        name = "Folder name",
        parentId = ""
    )

    private val testDirectory = FileItem(
        id = "testdirectory1",
        isDir = true,
        modificationDate = Instant.DISTANT_FUTURE,
        name = "Test directory 1",
        parentId = rootItem.id
    )

    private val user = User(firstName = "Allan", lastName = "McCulloch", rootItem = rootItem)
}