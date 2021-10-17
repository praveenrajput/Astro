package com.praveen.astro.di

import android.content.Context
import android.util.Log
import com.praveen.astro.AstrosQueries
import com.praveen.astro.Database
import com.praveen.astro.IssNow
import com.praveen.astro.IssNowQueries
import com.praveen.astro.data.network.api.AstrosApi
import com.praveen.astro.data.repository.AstroRepository
import com.praveen.astro.models.IssPosition
import com.praveen.astro.viewModels.AstrosViewModel
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.android.AndroidSqliteDriver
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalSerializationApi
fun applicationModule(enableLogging: Boolean) = module {
    single { provideJson() }
    single { provideHttpClient(get(), enableLogging = enableLogging) }
    single { provideAstrosApi(get()) }
    single { provideDriver(androidContext()) }
    single { provideDatabase(get()) }
    single { provideAstrosQueries(get()) }
    single { provideIssNowQueries(get()) }
    single { provideAstroRepository(get(), get(), get()) }
    viewModel { AstrosViewModel(get()) }
}

fun provideAstroRepository(
    astrosApi: AstrosApi,
    astrosQueries: AstrosQueries,
    issNowQueries: IssNowQueries
) = AstroRepository(astrosApi, astrosQueries, issNowQueries)

fun provideDriver(context: Context) = AndroidSqliteDriver(
    schema = Database.Schema,
    context = context,
    name = "astros.db"
)

@ExperimentalSerializationApi
fun provideDatabase(androidSqliteDriver: AndroidSqliteDriver) =
    Database(
        driver = androidSqliteDriver,
        IssNowAdapter = IssNow.Adapter(
            issPositionAdapter = issPositionAdapter
        )
    )

fun provideAstrosQueries(database: Database) =
    database
        .astrosQueries

fun provideIssNowQueries(database: Database) =
    database
        .issNowQueries

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

@ExperimentalSerializationApi
val issPositionAdapter = object : ColumnAdapter<IssPosition, String> {
    override fun decode(databaseValue: String): IssPosition {
        return if (databaseValue.isEmpty()) {
            IssPosition("", "")
        } else {
            Json.decodeFromString(databaseValue)
        }
    }

    override fun encode(value: IssPosition): String {
        return Json.encodeToString(value)
    }
}
