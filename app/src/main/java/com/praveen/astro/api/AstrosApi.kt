package com.praveen.astro.api

import com.praveen.astro.models.Astros
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class AstrosApi(private val client: HttpClient) {
    suspend fun getAstrosKtor(): Astros = client.get("http://api.open-notify.org/astros.json")
}
