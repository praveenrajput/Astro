package com.praveen.astro

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import com.praveen.astro.misc.AstrosListTag
import com.praveen.astro.ui.astros.AstroItem
import com.praveen.astro.utils.AstroFakeRepository
import com.praveen.astro.viewModels.AstrosViewModel
import org.junit.Rule
import org.junit.Test

class AstrosListTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val astrosFakeRepository = AstroFakeRepository()
    private val astrosViewModel = AstrosViewModel(astrosFakeRepository)

    @Test
    fun testAstroItem_astroNameExists() {
        composeTestRule.setContent {
            AstroItem(
                people = astrosFakeRepository.astros.first(),
                onItemClick = {}
            )
        }

        composeTestRule
            .onNode(
                hasText(astrosFakeRepository.astros.first().name)
            ).assertExists()
    }

    @Test
    fun testAstroItem_astroCraftExists() {
        composeTestRule.setContent {
            AstroItem(
                people = astrosFakeRepository.astros.first(),
                onItemClick = {}
            )
        }

        composeTestRule
            .onNode(
                hasText(astrosFakeRepository.astros.first().craft)
            ).assertExists()
    }

    @Test
    fun testAstrosList() {
        val astrosList = astrosFakeRepository.astros

        composeTestRule.setContent {
            AstrosList(
                paddingValues = PaddingValues(0.dp),
                astrosViewModel = astrosViewModel,
                onItemClick = {}
            )
        }

        val astrosListNode = composeTestRule.onNodeWithTag(AstrosListTag)

        astrosListNode
            .assertIsDisplayed()
            .onChildren()
            .assertCountEquals(astrosList.size)

        astrosList.forEachIndexed { index, astro ->
            val node = astrosListNode.onChildAt(index)
            node.assert(hasText(astro.name))
            node.assert(hasText(astro.craft))
        }
    }
}
