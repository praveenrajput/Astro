package com.praveen.astro.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IssNow(
    @SerialName("iss_position")
    val issPosition: IssPosition = IssPosition(),
    @SerialName("message")
    val message: String = "",
    @SerialName("timestamp")
    val timestamp: Long = 0L
)
