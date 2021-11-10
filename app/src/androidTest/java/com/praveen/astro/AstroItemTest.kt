package com.praveen.astro

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import com.praveen.astro.models.People
import com.praveen.astro.ui.astros.AstroItem
import org.junit.Rule
import org.junit.Test

class AstroItemTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val astro = People("Test Craft", "Test Name", "Cool Human", "Out of world")

    @Test
    fun testAstroItem_astroNameExists() {
        composeTestRule.setContent {
            AstroItem(
                people = astro,
                onItemClick = {}
            )
        }

        composeTestRule
            .onNode(
                hasText(astro.name)
            ).assertExists()
    }

    @Test
    fun testAstroItem_astroCraftExists() {
        composeTestRule.setContent {
            AstroItem(
                people = astro,
                onItemClick = {}
            )
        }

        composeTestRule
            .onNode(
                hasText(astro.craft)
            ).assertExists()
    }
}
