package com.victorude.currencyexchange.model

import com.victorude.currencyexchange.common.CurrencyCode
import com.victorude.currencyexchange.common.HistoricalDate
import com.victorude.currencyexchange.common.Rates
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/*
    {
        "success": true,
        "timestamp": 1519296206,
        "base": "USD",
        "date": "2022-06-07",
        "rates": {
            "GBP": 0.72007,
            "JPY": 107.346001,
            "EUR": 0.813399,
        }
    }
*/
@Serializable
data class LatestRatesResponse(
    val success: Boolean,
    @Contextual
    val timestamp: Long,
    val base: CurrencyCode,
    val date: HistoricalDate,
    val rates: Rates,
    val error: FixerError?
)