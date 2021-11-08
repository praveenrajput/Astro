package com.praveen.astro.network

import com.google.common.truth.Truth.assertThat
import com.praveen.astro.models.Astros
import com.praveen.astro.models.IssNow
import com.praveen.astro.utils.ApiClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import okio.buffer
import okio.source
import org.junit.Test
import java.nio.charset.StandardCharsets

@ExperimentalSerializationApi
class NetworkTest {
    @Test
    fun astroResponseTest() = runBlocking {
        val jsonData = getJsonDataString("astros")
        val astrosApiClient = ApiClient(getMockEngine(jsonDataString = jsonData))
        val astrosObject = astrosApiClient.json.decodeFromString<Astros>(jsonData)
        assertThat(astrosObject).isEqualTo(astrosApiClient.getAstroResponse())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun issNowResponseTest() = runBlocking {
        val jsonData = getJsonDataString("iss-now")
        val issApiClient = ApiClient(getMockEngine(jsonDataString = jsonData))
        val issNowObject = issApiClient.json.decodeFromString<IssNow>(jsonData)
        assertThat(issNowObject).isEqualTo(issApiClient.getIssNowResponse())
    }

    private fun getJsonDataString(fileName: String): String {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("$fileName.json")
        val data = inputStream.source().buffer()
        return data.readString(StandardCharsets.UTF_8)
    }

    private fun getMockEngine(jsonDataString: String): MockEngine {
        return MockEngine {
            respond(
                content = ByteReadChannel(jsonDataString),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
    }
}
