package com.hungtm.sdk.onboarding.data.remote

import com.hungtm.sdk.onboarding.domain.ErrorType
import com.hungtm.sdk.onboarding.domain.NetworkResult
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Executes an API call safely and maps exceptions to [NetworkResult].
 */
suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {
    return try {
        NetworkResult.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> {
                val message = throwable.message ?: ""
                val errorType = when {
                    message.contains("Encryption failed") || message.contains("Decryption failed") -> ErrorType.Encryption
                    throwable is SocketTimeoutException -> ErrorType.Network
                    else -> ErrorType.Network
                }
                NetworkResult.Error(errorType, message, throwable)
            }
            is HttpException -> {
                val errorType = when (throwable.code()) {
                    401 -> ErrorType.Unauthorized
                    in 500..599 -> ErrorType.Server
                    else -> ErrorType.Unknown
                }
                NetworkResult.Error(errorType, "HTTP ${throwable.code()}", throwable)
            }
            is JsonDataException -> {
                NetworkResult.Error(ErrorType.Serialization, throwable.message, throwable)
            }
            else -> {
                NetworkResult.Error(errorType = ErrorType.Unknown, message = throwable.message, throwable = throwable)
            }
        }
    }
}
