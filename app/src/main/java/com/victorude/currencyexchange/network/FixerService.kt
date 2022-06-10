package com.victorude.currencyexchange.network

import com.victorude.currencyexchange.common.Amount
import com.victorude.currencyexchange.common.CurrencyCode
import com.victorude.currencyexchange.common.HistoricalDate
import com.victorude.currencyexchange.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FixerService {
    @GET("/fixer/symbols")
    suspend fun getSymbols(@QueryMap options: Map<String, String> = mapOf()): GetSymbolsResponse

    @GET("/fixer/latest")
    suspend fun getLatestRates(@QueryMap options: Map<String, String>): LatestRatesResponse

    @GET("/fixer/{date}")
    suspend fun getHistoricalRates(
        @Path("date") date: HistoricalDate,
        @QueryMap options: Map<String, String>
    ): HistoricalRateResponse

    @GET("/fixer/convert")
    suspend fun convert(
        @Query("from") from: CurrencyCode,
        @Query("to") to: CurrencyCode,
        @Query("amount") amount: Amount
    ): ConvertCurrencyResponse

    @GET("/fixer/timeseries")
    suspend fun getTimeSeries(
        @Query("start_date") start: HistoricalDate,
        @Query("end_date") end: HistoricalDate
    ): TimeSeriesResponse

    @GET("/fixer/fluctuation")
    suspend fun getFluctuation(
        @Query("start_date") start: HistoricalDate,
        @Query("end_date") end: HistoricalDate
    ): FluctuationResponse
}
