package com.praveen.astro

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import com.praveen.astro.ui.common.ScreenTitle
import com.praveen.astro.utils.AstroScreen
import org.junit.Rule
import org.junit.Test

class CommonComponentsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun screenTitleTest() {
        composeTestRule.setContent {
            ScreenTitle(title = AstroScreen.Astros.title)
        }

        composeTestRule
            .onNode(
                hasText(AstroScreen.Astros.title)
            )
            .assertExists()
    }
}
