package com.hungtm.sdk.onboarding.domain.usecase

import com.hungtm.sdk.onboarding.domain.model.UserSession
import com.hungtm.sdk.onboarding.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow

class GetUserSessionUseCase(
    private val repository: OnboardingRepository
) {
    operator fun invoke(): Flow<UserSession?> {
        return repository.getUserSession()
    }
}
