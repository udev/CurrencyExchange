package com.victorude.currencyexchange.network

import com.google.gson.Gson
import com.victorude.currencyexchange.common.Amount
import com.victorude.currencyexchange.common.CurrencyCode
import com.victorude.currencyexchange.common.HistoricalDate
import com.victorude.currencyexchange.di.FixerModule
import com.victorude.currencyexchange.model.*
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@TestInstallIn(components = [SingletonComponent::class],
    replaces = [FixerModule::class])
@ExperimentalSerializationApi
@Module
object FakeFixerModule {

    @Provides
    fun provideFixerService(): FixerService {
        return fakeFixerService
    }

    @Provides
    fun provideFakeFixerRepository(
        fixerService: FixerService
    ): FakeFixerRepository {
        return FakeFixerRepository(fixerService)
    }
}

class FakeFixerRepository @Inject constructor(
    private val fixerService: FixerService
) : FixerService {

    override suspend fun getSymbols(options: Map<String, String>): GetSymbolsResponse =
        fixerService.getSymbols()

    override suspend fun getLatestRates(options: Map<String, String>): LatestRatesResponse =
        fixerService.getLatestRates(mapOf())

    override suspend fun getHistoricalRates(
        date: HistoricalDate,
        options: Map<String, String>
    ): HistoricalRateResponse =
        fixerService.getHistoricalRates(HistoricalDate(), mapOf())

    override suspend fun convert(
        from: CurrencyCode,
        to: CurrencyCode,
        amount: Amount
    ): ConvertCurrencyResponse =
        fixerService.convert(CurrencyCode(), CurrencyCode(), 0.0)

    override suspend fun getTimeSeries(
        start: HistoricalDate,
        end: HistoricalDate
    ): TimeSeriesResponse =
        fixerService.getTimeSeries(HistoricalDate(), HistoricalDate())


    override suspend fun getFluctuation(
        start: HistoricalDate,
        end: HistoricalDate
    ): FluctuationResponse =
        fixerService.getFluctuation(HistoricalDate(), HistoricalDate())
}

internal val fakeFixerService = object: FixerService {

    private val gson = Gson()

    override suspend fun getSymbols(options: Map<String, String>): GetSymbolsResponse =
        gson.fromJson(getSymbolsResponse, GetSymbolsResponse::class.java)

    override suspend fun getLatestRates(options: Map<String, String>): LatestRatesResponse =
        gson.fromJson(getRatesResponse, LatestRatesResponse::class.java)

    override suspend fun getHistoricalRates(
        date: HistoricalDate,
        options: Map<String, String>
    ): HistoricalRateResponse =
        gson.fromJson(getSymbolsResponse, HistoricalRateResponse::class.java)

    override suspend fun convert(
        from: CurrencyCode,
        to: CurrencyCode,
        amount: Amount
    ): ConvertCurrencyResponse =
        gson.fromJson(getSymbolsResponse, ConvertCurrencyResponse::class.java)

    override suspend fun getTimeSeries(
        start: HistoricalDate,
        end: HistoricalDate
    ): TimeSeriesResponse =
        gson.fromJson(getSymbolsResponse, TimeSeriesResponse::class.java)

    override suspend fun getFluctuation(
        start: HistoricalDate,
        end: HistoricalDate
    ): FluctuationResponse =
        gson.fromJson(getSymbolsResponse, FluctuationResponse::class.java)
}

private const val getSymbolsResponse: String =
    """
        {
            "success": true,
            "symbols": {
                "AED": "United Arab Emirates Dirham",
                "AFN": "Afghan Afghani",
                "ALL": "Albanian Lek",
                "AMD": "Armenian Dram",
                "ANG": "Netherlands Antillean Guilder",
                "AOA": "Angolan Kwanza",
                "ARS": "Argentine Peso",
                "AUD": "Australian Dollar",
                "AWG": "Aruban Florin",
                "AZN": "Azerbaijani Manat",
                "BAM": "Bosnia-Herzegovina Convertible Mark",
                "BBD": "Barbadian Dollar",
                "BDT": "Bangladeshi Taka",
                "BGN": "Bulgarian Lev",
                "BHD": "Bahraini Dinar",
                "BIF": "Burundian Franc",
                "BMD": "Bermudan Dollar",
                "BND": "Brunei Dollar",
                "BOB": "Bolivian Boliviano",
                "BRL": "Brazilian Real",
                "BSD": "Bahamian Dollar",
                "BTC": "Bitcoin",
                "BTN": "Bhutanese Ngultrum",
                "BWP": "Botswanan Pula",
                "BYN": "New Belarusian Ruble",
                "BYR": "Belarusian Ruble",
                "BZD": "Belize Dollar",
                "CAD": "Canadian Dollar",
                "CDF": "Congolese Franc",
                "CHF": "Swiss Franc",
                "CLF": "Chilean Unit of Account (UF)",
                "CLP": "Chilean Peso",
                "CNY": "Chinese Yuan",
                "COP": "Colombian Peso",
                "CRC": "Costa Rican Col\u00f3n",
                "CUC": "Cuban Convertible Peso",
                "CUP": "Cuban Peso",
                "CVE": "Cape Verdean Escudo",
                "CZK": "Czech Republic Koruna",
                "DJF": "Djiboutian Franc",
                "DKK": "Danish Krone",
                "DOP": "Dominican Peso",
                "DZD": "Algerian Dinar",
                "EGP": "Egyptian Pound",
                "ERN": "Eritrean Nakfa",
                "ETB": "Ethiopian Birr",
                "EUR": "Euro",
                "FJD": "Fijian Dollar",
                "FKP": "Falkland Islands Pound",
                "GBP": "British Pound Sterling",
                "GEL": "Georgian Lari",
                "GGP": "Guernsey Pound",
                "GHS": "Ghanaian Cedi",
                "GIP": "Gibraltar Pound",
                "GMD": "Gambian Dalasi",
                "GNF": "Guinean Franc",
                "GTQ": "Guatemalan Quetzal",
                "GYD": "Guyanaese Dollar",
                "HKD": "Hong Kong Dollar",
                "HNL": "Honduran Lempira",
                "HRK": "Croatian Kuna",
                "HTG": "Haitian Gourde",
                "HUF": "Hungarian Forint",
                "IDR": "Indonesian Rupiah",
                "ILS": "Israeli New Sheqel",
                "IMP": "Manx pound",
                "INR": "Indian Rupee",
                "IQD": "Iraqi Dinar",
                "IRR": "Iranian Rial",
                "ISK": "Icelandic Kr\u00f3na",
                "JEP": "Jersey Pound",
                "JMD": "Jamaican Dollar",
                "JOD": "Jordanian Dinar",
                "JPY": "Japanese Yen",
                "KES": "Kenyan Shilling",
                "KGS": "Kyrgystani Som",
                "KHR": "Cambodian Riel",
                "KMF": "Comorian Franc",
                "KPW": "North Korean Won",
                "KRW": "South Korean Won",
                "KWD": "Kuwaiti Dinar",
                "KYD": "Cayman Islands Dollar",
                "KZT": "Kazakhstani Tenge",
                "LAK": "Laotian Kip",
                "LBP": "Lebanese Pound",
                "LKR": "Sri Lankan Rupee",
                "LRD": "Liberian Dollar",
                "LSL": "Lesotho Loti",
                "LTL": "Lithuanian Litas",
                "LVL": "Latvian Lats",
                "LYD": "Libyan Dinar",
                "MAD": "Moroccan Dirham",
                "MDL": "Moldovan Leu",
                "MGA": "Malagasy Ariary",
                "MKD": "Macedonian Denar",
                "MMK": "Myanma Kyat",
                "MNT": "Mongolian Tugrik",
                "MOP": "Macanese Pataca",
                "MRO": "Mauritanian Ouguiya",
                "MUR": "Mauritian Rupee",
                "MVR": "Maldivian Rufiyaa",
                "MWK": "Malawian Kwacha",
                "MXN": "Mexican Peso",
                "MYR": "Malaysian Ringgit",
                "MZN": "Mozambican Metical",
                "NAD": "Namibian Dollar",
                "NGN": "Nigerian Naira",
                "NIO": "Nicaraguan C\u00f3rdoba",
                "NOK": "Norwegian Krone",
                "NPR": "Nepalese Rupee",
                "NZD": "New Zealand Dollar",
                "OMR": "Omani Rial",
                "PAB": "Panamanian Balboa",
                "PEN": "Peruvian Nuevo Sol",
                "PGK": "Papua New Guinean Kina",
                "PHP": "Philippine Peso",
                "PKR": "Pakistani Rupee",
                "PLN": "Polish Zloty",
                "PYG": "Paraguayan Guarani",
                "QAR": "Qatari Rial",
                "RON": "Romanian Leu",
                "RSD": "Serbian Dinar",
                "RUB": "Russian Ruble",
                "RWF": "Rwandan Franc",
                "SAR": "Saudi Riyal",
                "SBD": "Solomon Islands Dollar",
                "SCR": "Seychellois Rupee",
                "SDG": "Sudanese Pound",
                "SEK": "Swedish Krona",
                "SGD": "Singapore Dollar",
                "SHP": "Saint Helena Pound",
                "SLL": "Sierra Leonean Leone",
                "SOS": "Somali Shilling",
                "SRD": "Surinamese Dollar",
                "STD": "S\u00e3o Tom\u00e9 and Pr\u00edncipe Dobra",
                "SVC": "Salvadoran Col\u00f3n",
                "SYP": "Syrian Pound",
                "SZL": "Swazi Lilangeni",
                "THB": "Thai Baht",
                "TJS": "Tajikistani Somoni",
                "TMT": "Turkmenistani Manat",
                "TND": "Tunisian Dinar",
                "TOP": "Tongan Pa\u02bbanga",
                "TRY": "Turkish Lira",
                "TTD": "Trinidad and Tobago Dollar",
                "TWD": "New Taiwan Dollar",
                "TZS": "Tanzanian Shilling",
                "UAH": "Ukrainian Hryvnia",
                "UGX": "Ugandan Shilling",
                "USD": "United States Dollar",
                "UYU": "Uruguayan Peso",
                "UZS": "Uzbekistan Som",
                "VEF": "Venezuelan Bol\u00edvar Fuerte",
                "VND": "Vietnamese Dong",
                "VUV": "Vanuatu Vatu",
                "WST": "Samoan Tala",
                "XAF": "CFA Franc BEAC",
                "XAG": "Silver (troy ounce)",
                "XAU": "Gold (troy ounce)",
                "XCD": "East Caribbean Dollar",
                "XDR": "Special Drawing Rights",
                "XOF": "CFA Franc BCEAO",
                "XPF": "CFP Franc",
                "YER": "Yemeni Rial",
                "ZAR": "South African Rand",
                "ZMK": "Zambian Kwacha (pre-2013)",
                "ZMW": "Zambian Kwacha",
                "ZWL": "Zimbabwean Dollar"
            }
        }
    """

private const val getRatesResponse =
    """
        {
            "success": true,
            "timestamp": 1655147166,
            "base": "EUR",
            "date": "2022-06-13",
            "rates": {
                "AED": 3.830098,
                "AFN": 92.803646,
                "ALL": 119.863577,
                "AMD": 436.533176,
                "ANG": 1.88023,
                "AOA": 453.267209,
                "ARS": 127.526138,
                "AUD": 1.504114,
                "AWG": 1.876936,
                "AZN": 1.773896,
                "BAM": 1.949077,
                "BBD": 2.106381,
                "BDT": 97.964507,
                "BGN": 1.950532,
                "BHD": 0.393129,
                "BIF": 2118.851648,
                "BMD": 1.042742,
                "BND": 1.45065,
                "BOB": 7.172619,
                "BRL": 5.321338,
                "BSD": 1.043275,
                "BTC": 4.4578438e-05,
                "BTN": 81.466875,
                "BWP": 12.801052,
                "BYN": 3.521598,
                "BYR": 20437.74227,
                "BZD": 2.102914,
                "CAD": 1.342598,
                "CDF": 2090.697638,
                "CHF": 1.039687,
                "CLF": 0.032434,
                "CLP": 894.964461,
                "CNY": 7.043301,
                "COP": 4161.927237,
                "CRC": 714.537312,
                "CUC": 1.042742,
                "CUP": 27.632662,
                "CVE": 109.769449,
                "CZK": 24.738839,
                "DJF": 185.31607,
                "DKK": 7.43962,
                "DOP": 57.402577,
                "DZD": 153.042054,
                "EGP": 19.507592,
                "ERN": 15.64113,
                "ETB": 53.987942,
                "EUR": 1,
                "FJD": 2.286264,
                "FKP": 0.856194,
                "GBP": 0.858416,
                "GEL": 3.065503,
                "GGP": 0.856194,
                "GHS": 8.289358,
                "GIP": 0.856194,
                "GMD": 56.46452,
                "GNF": 9228.266371,
                "GTQ": 8.064357,
                "GYD": 218.268942,
                "HKD": 8.185451,
                "HNL": 25.552402,
                "HRK": 7.522548,
                "HTG": 119.45196,
                "HUF": 401.549547,
                "IDR": 15401.246502,
                "ILS": 3.613023,
                "IMP": 0.856194,
                "INR": 81.449773,
                "IQD": 1521.88188,
                "IRR": 44055.847136,
                "ISK": 138.444924,
                "JEP": 0.856194,
                "JMD": 159.862916,
                "JOD": 0.739312,
                "JPY": 140.009292,
                "KES": 122.261541,
                "KGS": 82.899065,
                "KHR": 4233.532028,
                "KMF": 491.778053,
                "KPW": 938.467594,
                "KRW": 1347.884757,
                "KWD": 0.320163,
                "KYD": 0.869408,
                "KZT": 456.33724,
                "LAK": 15333.520148,
                "LBP": 1592.734721,
                "LKR": 374.017715,
                "LRD": 158.496638,
                "LSL": 16.777473,
                "LTL": 3.078946,
                "LVL": 0.630744,
                "LYD": 5.00669,
                "MAD": 10.441498,
                "MDL": 19.910611,
                "MGA": 4186.608776,
                "MKD": 61.23645,
                "MMK": 1931.64237,
                "MNT": 3250.41978,
                "MOP": 8.435697,
                "MRO": 372.258698,
                "MUR": 45.980684,
                "MVR": 16.016315,
                "MWK": 1064.119353,
                "MXN": 21.321435,
                "MYR": 4.607355,
                "MZN": 66.558207,
                "NAD": 16.777543,
                "NGN": 432.87297,
                "NIO": 37.288477,
                "NOK": 10.32656,
                "NPR": 130.347478,
                "NZD": 1.664936,
                "OMR": 0.401459,
                "PAB": 1.043275,
                "PEN": 3.915484,
                "PGK": 3.670415,
                "PHP": 55.605302,
                "PKR": 212.458391,
                "PLN": 4.654123,
                "PYG": 7151.659913,
                "QAR": 3.802098,
                "RON": 4.944267,
                "RSD": 117.418011,
                "RUB": 59.983704,
                "RWF": 1075.588324,
                "SAR": 3.911867,
                "SBD": 8.453635,
                "SCR": 14.62361,
                "SDG": 476.006822,
                "SEK": 10.621469,
                "SGD": 1.453379,
                "SHP": 1.436274,
                "SLL": 13738.125074,
                "SOS": 608.410298,
                "SRD": 22.76462,
                "STD": 21582.65313,
                "SVC": 9.128371,
                "SYP": 2619.919968,
                "SZL": 16.778143,
                "THB": 36.394787,
                "TJS": 11.396573,
                "TMT": 3.660024,
                "TND": 3.217379,
                "TOP": 2.429896,
                "TRY": 17.975725,
                "TTD": 7.090962,
                "TWD": 31.05306,
                "TZS": 2430.63193,
                "UAH": 30.821804,
                "UGX": 3896.24474,
                "USD": 1.042742,
                "UYU": 41.101163,
                "UZS": 11470.161928,
                "VEF": 222969743556.03125,
                "VND": 24191.613299,
                "VUV": 120.389424,
                "WST": 2.752269,
                "XAF": 653.710733,
                "XAG": 0.049123,
                "XAU": 0.000571,
                "XCD": 2.818062,
                "XDR": 0.778724,
                "XOF": 652.230241,
                "XPF": 119.500512,
                "YER": 261.012245,
                "ZAR": 16.787941,
                "ZMK": 9385.915811,
                "ZMW": 17.605,
                "ZWL": 335.762483
            }
        }
    """