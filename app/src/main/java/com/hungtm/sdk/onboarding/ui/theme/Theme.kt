package com.hungtm.sdk.onboarding.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryVibrant,
    onPrimary = OnPrimaryVibrant,
    primaryContainer = PrimaryContainerVibrant,
    onPrimaryContainer = OnPrimaryContainerVibrant,
    secondary = SecondaryVibrant,
    onSecondary = OnSecondaryVibrant,
    secondaryContainer = SecondaryContainerVibrant,
    onSecondaryContainer = OnSecondaryContainerVibrant,
    tertiary = TertiaryVibrant,
    onTertiary = OnTertiaryVibrant,
    tertiaryContainer = TertiaryContainerVibrant,
    onTertiaryContainer = OnTertiaryContainerVibrant,
    error = ErrorVibrant,
    onError = OnErrorVibrant,
    errorContainer = ErrorContainerVibrant,
    onErrorContainer = OnErrorContainerVibrant,
    background = OnBackgroundVibrant, // Swapped for dark
    onBackground = BackgroundVibrant,
    surface = OnSurfaceVibrant,
    onSurface = SurfaceVibrant
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryVibrant,
    onPrimary = OnPrimaryVibrant,
    primaryContainer = PrimaryContainerVibrant,
    onPrimaryContainer = OnPrimaryContainerVibrant,
    secondary = SecondaryVibrant,
    onSecondary = OnSecondaryVibrant,
    secondaryContainer = SecondaryContainerVibrant,
    onSecondaryContainer = OnSecondaryContainerVibrant,
    tertiary = TertiaryVibrant,
    onTertiary = OnTertiaryVibrant,
    tertiaryContainer = TertiaryContainerVibrant,
    onTertiaryContainer = OnTertiaryContainerVibrant,
    error = ErrorVibrant,
    onError = OnErrorVibrant,
    errorContainer = ErrorContainerVibrant,
    onErrorContainer = OnErrorContainerVibrant,
    background = BackgroundVibrant,
    onBackground = OnBackgroundVibrant,
    surface = SurfaceVibrant,
    onSurface = OnSurfaceVibrant
)

@Composable
fun Sdk_onboardingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
