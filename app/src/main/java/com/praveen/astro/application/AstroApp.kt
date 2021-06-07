package com.praveen.astro.application

import android.app.Application
import com.praveen.astro.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AstroApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AstroApp)
            modules(applicationModule(false))
        }
    }
}
