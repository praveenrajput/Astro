package com.praveen.astro.data.network.api

import com.praveen.astro.models.Astros
import com.praveen.astro.models.IssNow
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class AstrosApi(
    private val client: HttpClient,
    private val baseUrl: String = "https://people-in-space-proxy.ew.r.appspot.com/"
) {
    suspend fun fetchAstros() = client.get<Astros>("$baseUrl/astros.json")
    suspend fun fetchIssPosition() = client.get<IssNow>("$baseUrl/iss-now.json")
}
