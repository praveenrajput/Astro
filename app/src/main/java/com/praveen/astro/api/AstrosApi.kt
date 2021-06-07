package com.praveen.astro.api

import com.praveen.astro.models.Astros
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class AstrosApi(
    private val client: HttpClient,
    private val baseUrl: String = "http://api.open-notify.org"
) {
    suspend fun fetchAstros() = client.get<Astros>("$baseUrl/astros.json")
    suspend fun fetchIssPosition() = client.get<Astros>("$baseUrl/iss-now.json")
}
