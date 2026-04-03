package com.hungtm.sdk.onboarding.domain

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val errorType: ErrorType, val message: String? = null, val throwable: Throwable? = null) : NetworkResult<Nothing>()
}

/**
 * Domain-level error types.
 */
sealed class ErrorType {
    data object Network : ErrorType()
    data object Serialization : ErrorType()
    data object Encryption : ErrorType()
    data object Server : ErrorType()
    data object Unauthorized : ErrorType()
    data object Unknown : ErrorType()
}
