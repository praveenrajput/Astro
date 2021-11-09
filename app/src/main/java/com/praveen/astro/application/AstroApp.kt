package com.praveen.astro.application

import android.app.Application
import com.praveen.astro.di.applicationModule
import com.praveen.astro.di.initKoin
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class AstroApp : Application() {
    @OptIn(ExperimentalSerializationApi::class)
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@AstroApp)
            modules(applicationModule(false))
        }
    }
}
