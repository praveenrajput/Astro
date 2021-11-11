package com.praveen.astro

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.praveen.astro.misc.AstroDetailTag
import com.praveen.astro.ui.details.AstroDetail
import com.praveen.astro.utils.AstroFakeRepository
import com.praveen.astro.utils.AstroScreen
import com.praveen.astro.viewModels.AstrosViewModel
import org.junit.Rule
import org.junit.Test

class AstroDetailTest {

    private val astrosFakeRepository = AstroFakeRepository()
    private val astrosViewModel = AstrosViewModel(astrosFakeRepository)
    private val astro = astrosFakeRepository.astros.first()

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testAstroDetailTitle() {
        composeTestRule.setContent {
            AstroDetail(
                astroName = astro.name,
                astrosViewModel = astrosViewModel
            )
        }

        composeTestRule.onNode(
            hasText(AstroScreen.Details.title)
        ).assertExists()
    }

    @Test
    fun testAstroDetail() {
        composeTestRule.setContent {
            AstroDetail(
                astroName = astro.name,
                astrosViewModel = astrosViewModel
            )
        }

        val tree = composeTestRule.onNodeWithTag(AstroDetailTag)
            .assertIsDisplayed()

        tree.assert(
            hasAnyChild(
                hasContentDescription(
                    astro.name
                )
            )
        )

        tree.assert(
            hasAnyChild(
                hasText(astro.name)
            )
        )

        tree.assert(
            hasAnyChild(
                hasText(astro.personBio)
            )
        )
    }
}
