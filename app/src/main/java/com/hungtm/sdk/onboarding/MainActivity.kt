package com.hungtm.sdk.onboarding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hungtm.sdk.onboarding.ui.onboarding.OnboardingScreen
import com.hungtm.sdk.onboarding.ui.theme.Sdk_onboardingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sdk_onboardingTheme {
                OnboardingScreen()
            }
        }
    }
}
