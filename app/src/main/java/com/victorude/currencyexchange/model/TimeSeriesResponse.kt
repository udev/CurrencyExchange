package com.victorude.currencyexchange.model

import com.victorude.currencyexchange.common.HistoricalDate
import com.victorude.currencyexchange.common.CurrencyCode
import com.victorude.currencyexchange.common.TimeSeries
import kotlinx.serialization.Serializable

/*
    {
        "success": true,
        "timeseries": true,
        "start_date": "2012-05-01",
        "end_date": "2012-05-03",
        "base": "EUR",
        "rates": {
            "2012-05-01":{
              "USD": 1.322891,
              "AUD": 1.278047,
              "CAD": 1.302303
            },
            "2012-05-02": {
              "USD": 1.315066,
              "AUD": 1.274202,
              "CAD": 1.299083
            },
            "2012-05-03": {
              "USD": 1.314491,
              "AUD": 1.280135,
              "CAD": 1.296868
            },
            [...]
        }
    }
*/
@Serializable
data class TimeSeriesResponse(
    val success: Boolean,
    val timeseries: Boolean,
    val start_date: HistoricalDate,
    val base: CurrencyCode,
    val rates: TimeSeries,
    val error: FixerError?
)