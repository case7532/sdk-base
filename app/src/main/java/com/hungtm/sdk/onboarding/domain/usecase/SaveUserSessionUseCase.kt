package com.hungtm.sdk.onboarding.domain.usecase

import com.hungtm.sdk.onboarding.domain.model.UserSession
import com.hungtm.sdk.onboarding.domain.repository.OnboardingRepository

class SaveUserSessionUseCase(
    private val repository: OnboardingRepository
) {
    suspend operator fun invoke(session: UserSession) {
        repository.saveUserSession(session)
    }
}
