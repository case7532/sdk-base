package com.hungtm.sdk.onboarding

import android.app.Application
import com.hungtm.sdk.onboarding.data.di.dataModule
import com.hungtm.sdk.onboarding.data.di.networkModule
import com.hungtm.sdk.onboarding.domain.di.domainModule
import com.hungtm.sdk.onboarding.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SdkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger()
            androidContext(this@SdkApplication)
            modules(
                networkModule,
                dataModule,
                domainModule,
                uiModule
            )
        }
    }
}
