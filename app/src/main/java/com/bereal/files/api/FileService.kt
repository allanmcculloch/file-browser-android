package com.bereal.files.api

import com.bereal.files.api.model.CreateFolderRequest
import com.bereal.files.api.model.FileItemDto
import com.bereal.files.api.model.UserDto
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface FileService {
    @GET("me")
    suspend fun getCurrentUser(): UserDto

    @GET("/items/{id}")
    suspend fun getItem(@Path(value = "id") id: String): List<FileItemDto>

    @GET("/items/{id}/data")
    suspend fun getItemData(@Path(value = "id") id: String): ResponseBody

    @POST("/items/{parentId}")
    @Headers("Content-Type: application/octet-stream")
    suspend fun uploadFile(
        @Path(value = "parentId") parentId: String,
        @Header("Content-Disposition") contentDisposition: String,
        @Body fileInputStream: RequestBody?
    ): Response<Void>

    @POST("/items/{parentId}")
    @Headers("Content-Type:application/json")
    suspend fun createFolder(@Path(value = "parentId") parentId: String, @Body createFolderRequest: CreateFolderRequest): Response<FileItemDto>

    @DELETE("/items/{id}")
    suspend fun deleteItem(@Path(value = "id") id: String): Response<Void>
}
