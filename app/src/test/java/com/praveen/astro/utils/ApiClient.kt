package com.praveen.astro.utils

import com.praveen.astro.models.Astros
import com.praveen.astro.models.IssNow
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import kotlinx.serialization.json.Json

class ApiClient(engine: HttpClientEngine) {
    val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }
    private val httpClient = HttpClient(engine) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
    }

    suspend fun getAstroResponse(): Astros =
        httpClient.get("https://github.com")

    suspend fun getIssNowResponse(): IssNow =
        httpClient.get("https://github.com")
}
