package com.victorude.currencyexchange.model

import com.victorude.currencyexchange.common.Amount
import com.victorude.currencyexchange.common.CurrencyCode
import com.victorude.currencyexchange.common.HistoricalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/*
    {
        "success": true,
        "query": {
            "from": "GBP",
            "to": "JPY",
            "amount": 25
        },
        "info": {
            "timestamp": 1519328414,
            "rate": 148.972231
        },
        "historical": "" // doesn't exist?
        "date": "2018-02-22"
        "result": 3724.305775
    }
*/
@Serializable
data class ConvertCurrencyResponse(
    val success: Boolean,
    val query: ConvertCurrencyQuery,
    val info: ConvertCurrencyInfo,
//    val historical: Boolean?, // appears to be deprecated https://apilayer.com/marketplace/fixer-api?txn=free&live_demo=show
    val date: HistoricalDate,
    val result: Amount,
    val error: FixerError?
)

@Serializable
data class ConvertCurrencyQuery(
    val from: CurrencyCode,
    val to: CurrencyCode,
    val amount: Amount /* = kotlin.Double */
)

@Serializable
data class ConvertCurrencyInfo(
    @Contextual
    val timestamp: Long,
    val rate: Amount
)