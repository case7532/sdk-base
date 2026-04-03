package com.hungtm.sdk.onboarding.domain

/**
 * Interface to provide the secret key for AES-256 encryption and decryption.
 * Implementation should handle secure storage or retrieval of this key.
 */
interface SecretKeyProvider {
    /**
     * Returns the 32-byte secret key for AES-256.
     * @return ByteArray of length 32.
     */
    fun getSecretKey(): ByteArray
}
