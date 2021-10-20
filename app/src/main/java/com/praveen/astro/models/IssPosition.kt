package com.praveen.astro.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IssPosition(
    @SerialName("latitude")
    val latitude: String = "0",
    @SerialName("longitude")
    val longitude: String = "0"
)
