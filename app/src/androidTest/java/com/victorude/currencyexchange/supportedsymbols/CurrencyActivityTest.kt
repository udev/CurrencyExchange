package com.victorude.currencyexchange.supportedsymbols

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.victorude.currencyexchange.feature.supportedsymbols.CurrencyActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrencyActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<CurrencyActivity>()
    // use createAndroidComposeRule<YourActivity>() if you need access to an activity
    // using createComposeRule() fails to resume the activity

    @Test
    fun myTest() {
        // Start the app
//        composeTestRule.setContent {
//            CurrencyExchangeTheme {
//                Root()
//            }
//        }

        composeTestRule.onNodeWithText("Currency Code").performTextInput("EUR")
        composeTestRule.onNodeWithText("EUR | Euro").assertIsDisplayed()
    }
}