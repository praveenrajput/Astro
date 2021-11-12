package com.praveen.astro.local

import com.google.common.truth.Truth.assertThat
import com.praveen.astro.AstrosQueries
import com.praveen.astro.Database
import com.praveen.astro.IssNow
import com.praveen.astro.IssNowQueries
import com.praveen.astro.People
import com.praveen.astro.di.issPositionAdapter
import com.praveen.astro.models.IssPosition
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LocalDatabaseTest {
    private lateinit var driver: JdbcSqliteDriver
    private lateinit var astrosQueries: AstrosQueries
    private lateinit var issNowQueries: IssNowQueries

    @ExperimentalSerializationApi
    @Before
    fun setUp() {
        driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val database = Database(
            driver,
            IssNow.Adapter(
                issPositionAdapter
            )
        )
        Database.Schema.create(driver)
        astrosQueries = database.astrosQueries
        issNowQueries = database.issNowQueries
    }

    @After
    fun tearDown() {
        driver.close()
    }

    @Test
    fun insertAstro() = runBlockingTest {
        val astro = People("Iss", "name", "bio", "image")
        astrosQueries.insertAstro(astro)
        val astroFromDatabase = astrosQueries.selectAstros().executeAsOne()
        assertThat(astroFromDatabase).isEqualTo(astro)
    }

    @Test
    fun deleteAllAstro() = runBlockingTest {
        val astro = People("Iss", "name", "bio", "image")
        astrosQueries.insertAstro(astro)
        astrosQueries.deleteAll()

        val astroFromDatabase = astrosQueries.selectAstros().executeAsList()
        assertThat(astroFromDatabase.isEmpty()).isTrue()
    }

    @Test
    fun selectAllAstro() = runBlockingTest {
        val astro1 = People("Iss1", "name", "bio", "image")
        val astro2 = People("Iss2", "name", "bio", "image")
        val astro3 = People("Iss3", "name", "bio", "image")
        astrosQueries.insertAstro(astro1)
        astrosQueries.insertAstro(astro2)
        astrosQueries.insertAstro(astro3)

        val astrosList = astrosQueries.selectAstros().executeAsList()
        assertThat(astrosList.size).isEqualTo(3)
    }

    @Test
    fun insertIssNow() = runBlockingTest {
        val issNow = IssNow(IssPosition("1", "1"), 0L)
        issNowQueries.insertIssPosition(issNow)
        val issNowFromDatabase = issNowQueries.selectIssPosition().executeAsOne()
        assertThat(issNowFromDatabase).isEqualTo(issNow)
    }

    @Test
    fun deleteIssNow() = runBlockingTest {
        val issNow = IssNow(IssPosition("1", "1"), 0L)
        issNowQueries.insertIssPosition(issNow)
        issNowQueries.deleteAll()

        val issNowFromDatabase = issNowQueries.selectIssPosition().executeAsList()
        assertThat(issNowFromDatabase.isEmpty()).isTrue()
    }

    @Test
    fun selectAllIssNow() = runBlocking {
        val issNow1 = IssNow(IssPosition("1", "1"), 0L)
        val issNow2 = IssNow(IssPosition("1", "1"), 0L)
        val issNow3 = IssNow(IssPosition("1", "1"), 0L)

        issNowQueries.insertIssPosition(issNow1)
        issNowQueries.insertIssPosition(issNow2)
        issNowQueries.insertIssPosition(issNow3)

        val issNowFromDatabase = issNowQueries.selectIssPosition().executeAsList()
        assertThat(issNowFromDatabase.size).isEqualTo(3)
    }
}
