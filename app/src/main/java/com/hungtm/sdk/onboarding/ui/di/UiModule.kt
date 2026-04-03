package com.hungtm.sdk.onboarding.ui.di

import com.hungtm.sdk.onboarding.ui.onboarding.OnboardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {
        OnboardingViewModel(
            getOnboardingStepUseCase = get(),
            saveOnboardingStepUseCase = get(),
            isOnboardingCompletedUseCase = get(),
            setOnboardingCompletedUseCase = get()
        )
    }
}
