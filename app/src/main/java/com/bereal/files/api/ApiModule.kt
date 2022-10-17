package com.bereal.files.api

import com.bereal.files.api.interceptors.BasicAuthInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private const val apiUrl = "http://163.172.147.216:8080"

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideFileService(json: Json): FileService {
        val client = OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor("noel", "foobar"))
            .build()

        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(apiUrl)
            .client(client)
            .build()
            .create(FileService::class.java)
    }

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }
}
