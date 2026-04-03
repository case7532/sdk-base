package com.hungtm.sdk.onboarding.domain.di

import com.hungtm.sdk.onboarding.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {
    factory { GetUserSessionUseCase(get()) }
    factory { SaveUserSessionUseCase(get()) }
    factory { ClearUserSessionUseCase(get()) }
    factory { GetOnboardingStepUseCase(get()) }
    factory { SaveOnboardingStepUseCase(get()) }
    factory { IsOnboardingCompletedUseCase(get()) }
    factory { SetOnboardingCompletedUseCase(get()) }
}
