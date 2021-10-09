package com.praveen.astro.data.repository

import android.util.Log
import com.praveen.astro.AstrosQueries
import com.praveen.astro.api.AstrosApi
import com.praveen.astro.models.People
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import java.net.UnknownHostException

class AstroRepository(
    private val astrosApi: AstrosApi,
    private val astrosQueries: AstrosQueries
) {
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
            Log.d("View Model", e.toString())
        }
    }
}
