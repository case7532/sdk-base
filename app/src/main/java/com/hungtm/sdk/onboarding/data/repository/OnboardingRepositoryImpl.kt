package com.hungtm.sdk.onboarding.data.repository

import android.content.SharedPreferences
import com.hungtm.sdk.onboarding.domain.model.UserSession
import com.hungtm.sdk.onboarding.domain.repository.OnboardingRepository
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OnboardingRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val moshi: Moshi
) : OnboardingRepository {

    private val userSessionAdapter = moshi.adapter(UserSession::class.java)

    private val _userSession = MutableStateFlow<UserSession?>(loadUserSession())
    private val _onboardingStep = MutableStateFlow(loadOnboardingStep())
    private val _isOnboardingCompleted = MutableStateFlow(loadOnboardingCompleted())

    override suspend fun saveUserSession(session: UserSession) {
        val json = userSessionAdapter.toJson(session)
        sharedPreferences.edit().putString(PREF_USER_SESSION, json).apply()
        _userSession.value = session
    }

    override fun getUserSession(): Flow<UserSession?> = _userSession.asStateFlow()

    override suspend fun clearUserSession() {
        sharedPreferences.edit().remove(PREF_USER_SESSION).apply()
        _userSession.value = null
    }

    override suspend fun saveOnboardingStep(step: Int) {
        sharedPreferences.edit().putInt(PREF_ONBOARDING_STEP, step).apply()
        _onboardingStep.value = step
    }

    override fun getOnboardingStep(): Flow<Int> = _onboardingStep.asStateFlow()

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        sharedPreferences.edit().putBoolean(PREF_ONBOARDING_COMPLETED, completed).apply()
        _isOnboardingCompleted.value = completed
    }

    override fun isOnboardingCompleted(): Flow<Boolean> = _isOnboardingCompleted.asStateFlow()

    private fun loadUserSession(): UserSession? {
        val json = sharedPreferences.getString(PREF_USER_SESSION, null) ?: return null
        return try {
            userSessionAdapter.fromJson(json)
        } catch (e: Exception) {
            null
        }
    }

    private fun loadOnboardingStep(): Int {
        return sharedPreferences.getInt(PREF_ONBOARDING_STEP, 0)
    }

    private fun loadOnboardingCompleted(): Boolean {
        return sharedPreferences.getBoolean(PREF_ONBOARDING_COMPLETED, false)
    }

    companion object {
        private const val PREF_USER_SESSION = "pref_user_session"
        private const val PREF_ONBOARDING_STEP = "pref_onboarding_step"
        private const val PREF_ONBOARDING_COMPLETED = "pref_onboarding_completed"
    }
}
