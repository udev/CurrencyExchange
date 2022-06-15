package com.victorude.currencyexchange.feature.supportedsymbols

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.victorude.currencyexchange.common.Amount
import com.victorude.currencyexchange.common.CurrencyCode
import com.victorude.currencyexchange.common.Symbols
import com.victorude.currencyexchange.model.ConvertCurrencyResponse
import com.victorude.currencyexchange.model.FixerHttpError
import com.victorude.currencyexchange.model.GetSymbolsResponse
import com.victorude.currencyexchange.model.LatestRatesResponse
import com.victorude.currencyexchange.network.FixerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CurrencyViewmodel @Inject constructor(
    private val fixerRepo: FixerRepository
) : ViewModel() {

    var messageState by mutableStateOf(MessageState(mutableListOf()))
        private set
    var symbols by mutableStateOf(SymbolsState())
        private set
    var rates by mutableStateOf(RatesState())
        private set
    var convertResult by mutableStateOf(ConvertResult())
        private set
    var filter by mutableStateOf("")
        private set

    private fun getSymbols() {
        symbols = symbols.copy(
            loading = true,
        )
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                fixerRepo.getSymbols()
            } catch (e: HttpException) {
                e.printStackTrace()
                e
            }
            when (result) {
                is GetSymbolsResponse -> {
                    symbols = when {
                        result.success -> {
                            symbols.copy(
                                symbolList = result.symbols,
                                loading = false
                            )
                        }

                        else -> {
                            addMessage()
                            symbols.copy(
                                loading = false
                            )
                        }
                    }
                }

                is HttpException -> {
                    addMessage(handleError(result))
                    symbols = symbols.copy(
                        loading = false
                    )
                }

                else -> {
                    addMessage()
                    symbols = symbols.copy(
                        loading = false
                    )
                }
            }
        }
    }

    fun getLatestRates(base: CurrencyCode, symbols: List<CurrencyCode>) {
        rates = rates.copy(
            loading = true
        )
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                fixerRepo.getLatestRates(
                    options = mapOf(
                        "base" to base,
                        "symbols" to symbols.joinToString(separator = ",") { it }
                    )
                )
            } catch (e: HttpException) {
                e.printStackTrace()
                e
            }
            when (result) {
                is LatestRatesResponse -> {
                    rates = when {
                        result.success -> {
                            rates.copy(
                                rates = result,
                                loading = false
                            )
                        }

                        else -> {
                            addMessage()
                            rates.copy(
                                loading = false
                            )
                        }
                    }
                }

                is HttpException -> {
                    addMessage(handleError(result))
                    rates = rates.copy(
                        loading = false
                    )
                }

                else -> {
                    addMessage()
                    rates = rates.copy(
                        loading = false
                    )
                }
            }
        }
    }

    fun convert(
        from: CurrencyCode,
        to: CurrencyCode,
        amount: Amount
    ) {
        convertResult = convertResult.copy(
            loading = true
        )
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                fixerRepo.convert(
                    from,
                    to,
                    amount
                )
            } catch (e: HttpException) {
                e.printStackTrace()
                e
            }
            when (result) {
                is ConvertCurrencyResponse -> {
                    convertResult = when {
                        result.success -> {
                            convertResult.copy(
                                convertResult = result,
                                loading = false
                            )
                        }

                        else -> {
                            addMessage()
                            convertResult.copy(
                                loading = false
                            )
                        }
                    }
                }

                is HttpException -> {
                    addMessage(handleError(result))
                    convertResult = convertResult.copy(
                        loading = false
                    )
                }

                else -> {
                    addMessage()
                    convertResult = convertResult.copy(
                        loading = false
                    )
                }
            }
        }
    }

    private fun handleError(result: HttpException): String =
        try {
            result.response()?.errorBody()?.string()?.let {
                Json.decodeFromString<FixerHttpError>(it).message
            } ?: run { OOPS }
        } catch (e: IOException) {
            e.printStackTrace()
            OOPS
        }

    fun applyFilter(query: CurrencyCode) {
        filter = query
    }

    fun clearFilter() {
        filter = ""
    }

    fun clearMessages() {
        messageState = messageState.copy(
            messages = mutableListOf()
        )
    }

    fun clearConversions() {
        convertResult = convertResult.copy(
            convertResult = null
        )
    }

    fun refresh() {
        getSymbols()
        getLatestRates("EUR", listOf())
    }

    private fun addMessage(message: String = OOPS) {
        if (messageState.messages.contains(message)) return
        messageState = messageState.copy(
            messages = messageState.messages.apply { add(message) }
        )
    }

    data class SymbolsState(
        val symbolList: Symbols = hashMapOf(),
        val loading: Boolean = false
    )

    data class RatesState(
        val rates: LatestRatesResponse? = null,
        val loading: Boolean = false
    )

    data class ConvertResult(
        val convertResult: ConvertCurrencyResponse? = null,
        val loading: Boolean = false
    )

    data class MessageState(
        val messages: MutableList<String>
    ){

}

    companion object {
        const val OOPS = "Oops! Something went wrong!" // should definitely be a string resource
    }
}

val CurrencyViewmodel.ConvertResult.isValid
    get() = this.convertResult?.info != null
