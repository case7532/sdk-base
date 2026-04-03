package com.hungtm.sdk.onboarding.domain.repository

import com.hungtm.sdk.onboarding.domain.model.UserSession
import kotlinx.coroutines.flow.Flow

interface OnboardingRepository {
    suspend fun saveUserSession(session: UserSession)
    fun getUserSession(): Flow<UserSession?>
    suspend fun clearUserSession()

    suspend fun saveOnboardingStep(step: Int)
    fun getOnboardingStep(): Flow<Int>
    suspend fun setOnboardingCompleted(completed: Boolean)
    fun isOnboardingCompleted(): Flow<Boolean>
}
