package com.praveen.astro

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.praveen.astro.misc.IssDetailTag
import com.praveen.astro.misc.MapViewTag
import com.praveen.astro.ui.issPosition.IssDetails
import com.praveen.astro.ui.issPosition.IssPositionSemanticKey
import com.praveen.astro.ui.issPosition.MapView
import com.praveen.astro.utils.AstroFakeRepository
import com.praveen.astro.utils.getFormattedTime
import org.junit.Rule
import org.junit.Test

class IssNowTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    private val issNow = AstroFakeRepository().issPosition

    @Test
    fun testIssNowDetail() {
        composeTestRule.setContent {
            IssDetails(
                issNow = issNow
            )
        }

        composeTestRule.apply {
            onNodeWithTag(IssDetailTag)
                .assertIsDisplayed()

            onNodeWithText(issNow.issPosition.latitude).assertExists()
            onNodeWithText(issNow.issPosition.longitude).assertExists()
            onNodeWithText(getFormattedTime(issNow.timestamp)).assertExists()
        }
    }

    @Test
    fun testMapView() {
        composeTestRule.setContent {
            MapView(position = issNow.issPosition)
        }

        composeTestRule.onNodeWithTag(MapViewTag)
            .assertIsDisplayed()

        composeTestRule
            .onNode(SemanticsMatcher.expectValue(IssPositionSemanticKey, issNow.issPosition))
            .assertExists()
    }
}
