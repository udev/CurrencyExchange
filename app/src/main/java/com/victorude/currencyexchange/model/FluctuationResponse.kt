package com.victorude.currencyexchange.model

import com.victorude.currencyexchange.common.*
import kotlinx.serialization.Serializable

/*
    {
        "success":true,
        "fluctuation":true,
        "start_date":"2018-02-25",
        "end_date":"2018-02-26",
        "base":"EUR",
        "rates":{
            "USD":{
                "start_rate":1.228952,
                "end_rate":1.232735,
                "change":0.0038,
                "change_pct":0.3078
            },
            "JPY":{
                "start_rate":131.587611,
                "end_rate":131.651142,
                "change":0.0635,
                "change_pct":0.0483
            },
            [...]
        }
    }
*/
@Serializable
data class FluctuationResponse(
    val success: Boolean,
    val fluctuation: Boolean,
    val start_date: HistoricalDate /* = kotlin.String */,
    val end_date: HistoricalDate /* = kotlin.String */,
    val base: CurrencyCode,
    val rates: Fluctuations,
    val error: FixerError?
)

@Serializable
data class Fluctuation(
    val start_rate: FluctuationRate,
    val end_rate: FluctuationRate,
    val change: Amount,
    val change_pct: FluctuationPercent
)