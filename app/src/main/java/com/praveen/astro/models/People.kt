package com.praveen.astro.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class People(
    @SerialName("craft")
    val craft: String,
    @SerialName("name")
    val name: String,
    @SerialName("personBio")
    val personBio: String = "",
    @SerialName("personImageUrl")
    val personImageUrl: String = ""
)
