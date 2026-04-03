package com.hungtm.sdk.onboarding.domain.usecase

import com.hungtm.sdk.onboarding.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow

class GetOnboardingStepUseCase(private val repository: OnboardingRepository) {
    operator fun invoke(): Flow<Int> = repository.getOnboardingStep()
}

class SaveOnboardingStepUseCase(private val repository: OnboardingRepository) {
    suspend operator fun invoke(step: Int) = repository.saveOnboardingStep(step)
}

class IsOnboardingCompletedUseCase(private val repository: OnboardingRepository) {
    operator fun invoke(): Flow<Boolean> = repository.isOnboardingCompleted()
}

class SetOnboardingCompletedUseCase(private val repository: OnboardingRepository) {
    suspend operator fun invoke(completed: Boolean) = repository.setOnboardingCompleted(completed)
}

class ClearUserSessionUseCase(private val repository: OnboardingRepository) {
    suspend operator fun invoke() = repository.clearUserSession()
}
