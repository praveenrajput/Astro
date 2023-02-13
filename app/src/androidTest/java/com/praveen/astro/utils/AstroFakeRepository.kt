package com.praveen.astro.utils

import com.praveen.astro.data.repository.AstrosRepositoryInterface
import com.praveen.astro.models.IssNow
import com.praveen.astro.models.IssPosition
import com.praveen.astro.models.People
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AstroFakeRepository : AstrosRepositoryInterface {
    val astros = listOf(
        People("Test Craft", "Test Name"),
        People("Test Craft1", "Test Name1")
    )

    val issPosition = IssNow(
        issPosition = IssPosition("15.15", "30.30")
    )

    override fun getIssNow(): Flow<IssNow> {
        return flowOf(issPosition)
    }

    override fun getPeopleWithName(astroName: String): Flow<People> {
        return flowOf(astros.find { it.name == astroName } ?: People())
    }

    override fun getPeople(): Flow<List<People>> {
        return flowOf(astros)
    }

    override suspend fun refreshPeople() {
        // no implementation needed
    }

    override suspend fun refreshIss() {
        // no implementation needed
    }
}
