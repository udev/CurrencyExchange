package com.victorude.currencyexchange.model

import com.victorude.currencyexchange.common.HistoricalDate
import kotlinx.serialization.Serializable

/*
    {
        "success": true,
        "historical": true,
        "date": "2013-12-24",
        "timestamp": 1387929599,
        "base": "GBP",
        "rates": {
            "USD": 1.636492,
            "EUR": 1.196476,
            "CAD": 1.739516
        }
    }
*/
@Serializable
data class HistoricalRateResponse(
    val success: Boolean,
    val historical: Boolean,
    val date: HistoricalDate,
    val error: FixerError?
)