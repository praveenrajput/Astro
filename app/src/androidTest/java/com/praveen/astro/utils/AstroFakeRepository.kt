package com.praveen.astro.utils

import com.praveen.astro.models.IssPosition
import com.praveen.astro.models.People

class AstroFakeRepository {
    val astros = listOf(
        People("Test Craft", "Test Name"),
        People("Test Craft1", "Test Name1"),
    )

    val issPosition = IssPosition("15.15", "30.30")
}
