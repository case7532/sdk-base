package com.hungtm.sdk.onboarding.domain.model

data class UserSession(
    val userId: String,
    val authToken: String,
    val refreshToken: String? = null
)
