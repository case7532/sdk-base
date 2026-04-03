package com.hungtm.sdk.onboarding.data.di

import com.hungtm.sdk.onboarding.data.remote.AesInterceptor
import com.hungtm.sdk.onboarding.data.security.AesEncryptor
import com.hungtm.sdk.onboarding.domain.SecretKeyProvider
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { AesEncryptor() }
    
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        AesInterceptor(
            secretKeyProvider = get(),
            aesEncryptor = get()
        )
    }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AesInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.example.com/") // Placeholder Base URL
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }
}
