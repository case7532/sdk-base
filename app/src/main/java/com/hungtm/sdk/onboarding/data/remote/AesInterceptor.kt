package com.hungtm.sdk.onboarding.data.remote

import com.hungtm.sdk.onboarding.data.security.AesEncryptor
import com.hungtm.sdk.onboarding.domain.SecretKeyProvider
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.io.IOException

/**
 * Custom OkHttp Interceptor that encrypts request bodies and decrypts response bodies using AES-256.
 */
class AesInterceptor(
    private val secretKeyProvider: SecretKeyProvider,
    private val aesEncryptor: AesEncryptor
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val secretKey = secretKeyProvider.getSecretKey()

        // 1. Encrypt Request Body
        val encryptedRequest = encryptRequest(request, secretKey)

        // 2. Proceed with the call
        val response = try {
            chain.proceed(encryptedRequest)
        } catch (e: Exception) {
            throw IOException("Network error: ${e.message}", e)
        }

        // 3. Decrypt Response Body
        return decryptResponse(response, secretKey)
    }

    private fun encryptRequest(request: Request, secretKey: ByteArray): Request {
        val body = request.body ?: return request
        if (body.contentLength() == 0L) return request

        return try {
            val buffer = Buffer()
            body.writeTo(buffer)
            val plainText = buffer.readUtf8()
            
            val encryptedText = aesEncryptor.encrypt(plainText, secretKey)
            
            val newBody = encryptedText.toRequestBody("text/plain".toMediaTypeOrNull())
            request.newBuilder()
                .method(request.method, newBody)
                .build()
        } catch (e: Exception) {
            throw IOException("Encryption failed: ${e.message}", e)
        }
    }

    private fun decryptResponse(response: Response, secretKey: ByteArray): Response {
        if (!response.isSuccessful) return response
        
        val body = response.body ?: return response
        val encryptedText = body.string()
        
        if (encryptedText.isEmpty()) return response

        return try {
            val decryptedText = aesEncryptor.decrypt(encryptedText, secretKey)
            val contentType = response.header("Content-Type")?.toMediaTypeOrNull() ?: "application/json".toMediaTypeOrNull()
            
            val newBody = decryptedText.toResponseBody(contentType)
            response.newBuilder()
                .body(newBody)
                .build()
        } catch (e: Exception) {
            throw IOException("Decryption failed: ${e.message}", e)
        }
    }
}
