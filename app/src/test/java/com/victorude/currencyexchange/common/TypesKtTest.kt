package com.victorude.currencyexchange.common

import org.junit.Test

class TypesKtTest {
    @Test
    fun getDisplayCurrencySign() {
        val decoratedDollarSign = "USD".getDisplayCurrencySign()
        assert(decoratedDollarSign == "$") { "expected = '\$', actual = there should be space following the symbol '$decoratedDollarSign'" }
        val decoratedMexicanDollar = "MXN".getDisplayCurrencySign()
        assert(decoratedMexicanDollar == "MX$ ") { "expected = 'MX\$ ', actual = there should be space following the symbol '$decoratedMexicanDollar'" }
    }

    @Test
    fun getCurrencySign() {
        val undecoratedDollarSign = "USD".getCurrencySign()
        assert(undecoratedDollarSign == "$") { "expected = '\$', actual = '$undecoratedDollarSign'" }
        val undecoratedMexicanDollar = "MXN".getCurrencySign()
        assert(undecoratedMexicanDollar == "MX$") { "expected = 'MX\$', actual = there should be space following the symbol '$undecoratedMexicanDollar'" }
    }

    @Test
    fun containsSpecialCharacter() {
        val yenCurrencySymbol = "¥1000"
        val foundYenSymbol = yenCurrencySymbol.containsSpecialCharacter()
        assert(foundYenSymbol) { "expected = true, actual = $foundYenSymbol '$yenCurrencySymbol'" }
        val nigerianNairaCurrency = "NGN"
        val doesNotContainSpecialCharacter = !nigerianNairaCurrency.containsSpecialCharacter()
        assert(doesNotContainSpecialCharacter) { "expected = true, actual = $doesNotContainSpecialCharacter '$nigerianNairaCurrency'" }
    }

    @Test
    fun getDisplayAmount() {
        val dollarString = "1.00"
        val oneDollar = 1.0000000.getDisplayAmount()
        assert(oneDollar == dollarString) { "expected = '$dollarString', actual = '$oneDollar'" }
        val euroString = "9.99"
        val nineNinetyNineEuro = 9.99.getDisplayAmount()
        assert(nineNinetyNineEuro == euroString) { "expected = '$euroString', actual = '$nineNinetyNineEuro'" }
    }

    @Test
    fun toCurrencyCode() {
        val dop = "dopamine".toCurrencyCode()
        assert(dop == "DOP") { "expected = 'DOP', actual = $dop" }
        val gel = "gelatin".toCurrencyCode()
        assert(gel == "GEL") { "expected = 'GEL', actual = $gel" }
        val rub = "rUby".toCurrencyCode()
        assert(rub == "RUB") { "expected = 'RUB', actual = $rub" }
    }

    @Test
    fun toAmountOrOne() {
        val empty = "".toAmountOrOne()
        assert(empty == "1") { "expected = '1', actual = $empty" }
        val one = "1".toAmountOrOne()
        assert(one == "1") { "expected = '1', actual = $one" }
        val nineNineNine = "999".toAmountOrOne()
        assert(nineNineNine == "999") { "expected = '999', actual = $nineNineNine" }
    }
}