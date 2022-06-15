package com.victorude.currencyexchange.feature.supportedsymbols

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CurrencyActivityTest {
    // interesting approach via: https://medium.com/nerd-for-tech/writing-an-integration-test-with-jetpack-compose-and-dagger-hilt-8ef888c1a23d
    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<CurrencyActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.activity.setContent {
            Root(composeTestRule.activity.viewModels<CurrencyViewmodel>().value)
        }
    }

    @Test
    fun filterSymbolListTest() {
        composeTestRule.apply {
            waitUntil {
                onNodeWithTag("symbolList").onChildren().fetchSemanticsNodes().size > 1
            }
            onNodeWithText("Currency Code").performTextInput("EUR")
            waitUntil {
                onNodeWithTag("symbolList").onChildren().fetchSemanticsNodes().size == 1
            }
            onNodeWithText("EUR").printToLog("WTF")
            onNodeWithText("EUR").assertIsDisplayed()
            onNodeWithTag("symbolList").onChildren().onFirst().apply {
                assertIsDisplayed()
                assertTextContains(value = "EUR", substring = true)
                performClick()
            }
            onNodeWithTag("amountField").assertIsDisplayed()
        }
    }
}