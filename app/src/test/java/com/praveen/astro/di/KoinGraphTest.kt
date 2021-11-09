package com.praveen.astro.di

import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.stopKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.check.checkModules
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class KoinGraphTest : AutoCloseKoinTest() {
    private val context = getApplicationContext<Context>()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Before
    fun setUp() {
        // prevents KoinAppAlreadyStartedException
        stopKoin()
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun testAppModules() {
        initKoin {
            androidContext(context)
            modules(applicationModule())
        }.checkModules()
    }
}
