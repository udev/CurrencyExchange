package com.victorude.currencyexchange.model

import com.victorude.currencyexchange.common.Symbols
import kotlinx.serialization.Serializable

/*
    {
      "success": true,
      "symbols": {
        "AED": "United Arab Emirates Dirham",
        "AFN": "Afghan Afghani",
        "ALL": "Albanian Lek",
        "AMD": "Armenian Dram",
        [...]
        }
    }
*/
@Serializable
data class GetSymbolsResponse(
    val success: Boolean,
    val symbols: Symbols,
    val error: FixerError?,
)
