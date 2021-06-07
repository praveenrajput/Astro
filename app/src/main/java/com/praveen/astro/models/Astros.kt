package com.praveen.astro.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Astros(
    @SerialName("message")
    val message: String = "",
    @SerialName("number")
    val number: Int = 0,
    @SerialName("people")
    val people: List<People> = listOf()
)
