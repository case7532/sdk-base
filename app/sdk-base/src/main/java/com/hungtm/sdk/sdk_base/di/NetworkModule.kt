package com.hungtm.sdk.sdk_base.di

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

interface ApiService {
}

fun createJson(): Json = Json {
    ignoreUnknownKeys = true   // tránh crash khi server trả thêm field mới
    isLenient = true           // chấp nhận JSON không strict
    encodeDefaults = true      // serialize cả field có giá trị default
    coerceInputValues = true   // null → default value thay vì crash
}

// Builder functions - Koin resolves parameters automatically
fun createOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

fun createRetrofit(client: OkHttpClient, json: Json): Retrofit? {
    val contentType = "application/json".toMediaType()
    return Retrofit.Builder()
        .baseUrl("https://api.example.com/")
        .client(client)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()
}

fun createApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)

val networkModule = module {
    single { createJson() }
    single { createOkHttpClient() }
    single { createRetrofit(get(), get()) } // Koin tự inject OkHttpClient + Json
    single { createApiService(get()) }      // Koin tự inject Retrofit
}