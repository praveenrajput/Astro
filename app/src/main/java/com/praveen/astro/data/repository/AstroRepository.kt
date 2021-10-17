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
import java.net.UnknownHostException

class AstroRepository(
    private val astrosApi: AstrosApi,
    private val astrosQueries: AstrosQueries,
    private val issNowQueries: IssNowQueries
) {

    suspend fun getIssNow(): Flow<IssNow> {
        refreshIssNow()
        return issNowQueries.selectIssPosition(
            mapper = { issPosition, timeStamp ->
                IssNow(issPosition = issPosition, timestamp = timeStamp)
            }
        ).asFlow().mapToOne()
    }

    private suspend fun refreshIssNow() {
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

    suspend fun getPeople(): Flow<List<People>> {
        refreshPeople()
        return astrosQueries.selectAstros(
            mapper = { name, craft ->
                People(name = name, craft = craft)
            }
        ).asFlow().mapToList()
    }

    private suspend fun refreshPeople() {
        try {
            val astrosData = astrosApi.fetchAstros()
            astrosQueries.transaction {
                astrosQueries.deleteAll()
                astrosData.people.forEach {
                    astrosQueries.insert(it.name, it.craft)
                }
            }
        } catch (e: UnknownHostException) {
            Log.d("Astros Repo", e.toString())
        }
    }
}
