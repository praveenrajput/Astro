package com.praveen.astro.di

import android.util.Log
import com.praveen.astro.api.AstrosApi
import com.praveen.astro.viewModels.AstrosViewModel
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun applicationModule(enableLogging: Boolean) = module {
    single { provideJson() }
    single { provideHttpClient(get(), enableLogging = enableLogging) }
    single { provideAstrosApi(get()) }
    viewModel { AstrosViewModel(get()) }
}

fun provideJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
}

fun provideHttpClient(json: Json, enableLogging: Boolean) = HttpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer(json)
    }

    if (enableLogging) {
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Logger ktor =>", message)
                }
            }
            level = LogLevel.ALL
        }
    }
}

fun provideAstrosApi(httpClient: HttpClient): AstrosApi {
    return AstrosApi(httpClient)
}
