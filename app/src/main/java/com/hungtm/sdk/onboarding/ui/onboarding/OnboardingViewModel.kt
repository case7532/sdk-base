package com.hungtm.sdk.onboarding.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hungtm.sdk.onboarding.domain.usecase.GetOnboardingStepUseCase
import com.hungtm.sdk.onboarding.domain.usecase.IsOnboardingCompletedUseCase
import com.hungtm.sdk.onboarding.domain.usecase.SaveOnboardingStepUseCase
import com.hungtm.sdk.onboarding.domain.usecase.SetOnboardingCompletedUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class OnboardingUiState(
    val currentStep: Int = 0,
    val isCompleted: Boolean = false
)

class OnboardingViewModel(
    private val getOnboardingStepUseCase: GetOnboardingStepUseCase,
    private val saveOnboardingStepUseCase: SaveOnboardingStepUseCase,
    private val isOnboardingCompletedUseCase: IsOnboardingCompletedUseCase,
    private val setOnboardingCompletedUseCase: SetOnboardingCompletedUseCase
) : ViewModel() {

    val uiState: StateFlow<OnboardingUiState> = combine(
        getOnboardingStepUseCase(),
        isOnboardingCompletedUseCase()
    ) { step, completed ->
        OnboardingUiState(currentStep = step, isCompleted = completed)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = OnboardingUiState()
    )

    fun nextStep() {
        viewModelScope.launch {
            val nextStep = uiState.value.currentStep + 1
            if (nextStep < 3) {
                saveOnboardingStepUseCase(nextStep)
            } else {
                completeOnboarding()
            }
        }
    }

    fun previousStep() {
        viewModelScope.launch {
            val prevStep = uiState.value.currentStep - 1
            if (prevStep >= 0) {
                saveOnboardingStepUseCase(prevStep)
            }
        }
    }

    private fun completeOnboarding() {
        viewModelScope.launch {
            setOnboardingCompletedUseCase(true)
        }
    }
}
