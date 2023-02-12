package com.praveen.astro.data.repository

import android.util.Log
import com.praveen.astro.AstrosQueries
import com.praveen.astro.IssNowQueries
import com.praveen.astro.data.network.api.AstrosApi
import com.praveen.astro.models.IssNow
import com.praveen.astro.models.People
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.net.UnknownHostException

interface AstrosRepositoryInterface {
    fun getIssNow(): Flow<IssNow>
    fun getPeopleWithName(astroName: String): Flow<People>
    fun getPeople(): Flow<List<People>>
    suspend fun refreshPeople()
    suspend fun refreshIss()
}

class AstroRepository : KoinComponent, AstrosRepositoryInterface {

    private val astrosApi: AstrosApi by inject()
    private val astrosQueries: AstrosQueries by inject()
    private val issNowQueries: IssNowQueries by inject()

    override fun getIssNow(): Flow<IssNow> {
        return issNowQueries.selectIssPosition(
            mapper = { issPosition, timeStamp ->
                IssNow(issPosition = issPosition, timestamp = timeStamp)
            }
        ).asFlow().mapToOne()
    }

    override fun getPeopleWithName(astroName: String): Flow<People> {
        return astrosQueries.selectAstro(
            astroName,
            mapper = { name, craft, personBio, personImageUrl ->
                People(
                    name = name,
                    craft = craft,
                    personBio = personBio,
                    personImageUrl = personImageUrl
                )
            }
        ).asFlow().mapToOne()
    }

    override fun getPeople(): Flow<List<People>> {
        return astrosQueries.selectAstros(
            mapper = { name, craft, personBio, personImageUrl ->
                People(
                    name = name,
                    craft = craft,
                    personBio = personBio,
                    personImageUrl = personImageUrl
                )
            }
        ).asFlow().mapToList()
    }

    override suspend fun refreshPeople() {
        try {
            val astrosData = astrosApi.fetchAstros()
            astrosQueries.transaction {
                astrosQueries.deleteAll()
                astrosData.people.forEach {
                    astrosQueries.insert(it.name, it.craft, it.personBio, it.personImageUrl)
                }
            }
        } catch (e: UnknownHostException) {
            Log.d("Astros Repo", e.toString())
        }
    }

    override suspend fun refreshIss() {
        try {
            val issNowData = astrosApi.fetchIssPosition()
            issNowQueries.transaction {
                issNowQueries.deleteAll()
                issNowQueries.insert(
                    issNowData.issPosition,
                    issNowData.timestamp
                )
            }
        } catch (e: UnknownHostException) {
            Log.d("Astros Repo", e.toString())
        }
    }
}
