package com.hungtm.sdk.sdk_base

import android.content.Context
import com.hungtm.sdk.sdk_base.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class Base {
    fun init(): Base{
        loadKoinModules(listOf(
            networkModule,
            baseModule
        ))
        return this
    }
    fun start(){
    }
}