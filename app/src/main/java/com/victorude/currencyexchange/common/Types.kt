package com.victorude.currencyexchange.common

import com.victorude.currencyexchange.model.Fluctuation

typealias CurrencyCode = String
typealias Currency = String
typealias Symbol = Map.Entry<CurrencyCode, Currency>
typealias Symbols = HashMap<CurrencyCode, Currency>
typealias TimeSeries = HashMap<HistoricalDate, HashMap<CurrencyCode, Amount>>
typealias Amount = Double
typealias Rates = HashMap<CurrencyCode, Amount>
typealias HistoricalDate = String
typealias FluctuationRate = Double
typealias FluctuationPercent = Double
typealias Fluctuations = HashMap<CurrencyCode, Fluctuation>

fun CurrencyCode.getDisplayCurrencySign(): String =
    this.getCurrencySign().let {
        when {
            it.contains(Regex("[a-zA-Z]")) -> "$it "
            else -> it
        }
    }

fun CurrencyCode.getCurrencySign(): String =
    try {
        java.util.Currency.getInstance(this).symbol
    } catch (e: NullPointerException) {
        e.printStackTrace()
        ""
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        ""
    }

fun CurrencyCode.containsSpecialCharacter() = this.contains(Regex("[^A-Z]"))

fun Amount.getDisplayAmount(): String = String.format("%.2f", this)

fun String.toCurrencyCode() =
    this.trim()
        .replace(Regex("[^a-zA-Z]"), "")
        .uppercase()
        .take(3)

fun String.toAmountOrOne(): String =
    try {
        this.trim()
            .replace(Regex("[^0-9]"), "")
            .toInt()
            .toString()
    } catch (e: NumberFormatException ) {
        e.printStackTrace()
        "1"
    }