package com.victorude.currencyexchange.network

import com.victorude.currencyexchange.common.Amount
import com.victorude.currencyexchange.common.CurrencyCode
import com.victorude.currencyexchange.common.HistoricalDate
import com.victorude.currencyexchange.model.*
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class FixerRepository @Inject constructor(
    private val fixerService: FixerService
) : FixerService {
    override suspend fun getSymbols(options: Map<String, String>): GetSymbolsResponse =
        fixerService.getSymbols(options)

    override suspend fun getLatestRates(options: Map<String, String>): LatestRatesResponse =
        fixerService.getLatestRates(options)

    override suspend fun getHistoricalRates(
        date: HistoricalDate,
        options: Map<String, String>
    ): HistoricalRateResponse = fixerService.getHistoricalRates(date, options)

    override suspend fun convert(
        from: CurrencyCode,
        to: CurrencyCode,
        amount: Amount
    ): ConvertCurrencyResponse = fixerService.convert(from, to, amount)

    override suspend fun getTimeSeries(
        start: HistoricalDate,
        end: HistoricalDate
    ): TimeSeriesResponse = fixerService.getTimeSeries(start, end)

    override suspend fun getFluctuation(
        start: HistoricalDate,
        end: HistoricalDate
    ): FluctuationResponse = fixerService.getFluctuation(start, end)
}