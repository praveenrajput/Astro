package com.praveen.astro.di

import android.content.Context
import android.util.Log
import com.praveen.astro.AstrosQueries
import com.praveen.astro.Database
import com.praveen.astro.api.AstrosApi
import com.praveen.astro.data.repository.AstroRepository
import com.praveen.astro.viewModels.AstrosViewModel
import com.squareup.sqldelight.android.AndroidSqliteDriver
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun applicationModule(enableLogging: Boolean) = module {
    single { provideJson() }
    single { provideHttpClient(get(), enableLogging = enableLogging) }
    single { provideAstrosApi(get()) }
    single { provideDriver(androidContext()) }
    single { provideQueries(get()) }
    single { provideAstroRepository(get(), get()) }
    viewModel { AstrosViewModel(get()) }
}

fun provideAstroRepository(
    astrosApi: AstrosApi,
    astrosQueries: AstrosQueries
) = AstroRepository(astrosApi, astrosQueries)

fun provideDriver(context: Context) = AndroidSqliteDriver(
    schema = Database.Schema,
    context = context,
    name = "astros.db"
)

fun provideQueries(androidSqliteDriver: AndroidSqliteDriver) =
    Database(androidSqliteDriver)
        .astrosQueries

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
