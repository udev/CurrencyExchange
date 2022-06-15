package com.victorude.currencyexchange.feature.supportedsymbols

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.victorude.currencyexchange.R
import com.victorude.currencyexchange.common.*
import com.victorude.currencyexchange.ui.theme.CurrencyExchangeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrencyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyExchangeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Root()
                }
            }
        }
    }
}

@Composable
fun Root(
    viewModel: CurrencyViewmodel = hiltViewModel()
) {
    val navController = rememberNavController()
    val messageList = viewModel.messageState

    NavHost(navController = navController, startDestination = "symbols") {
        composable("symbols") {
            SupportedSymbols(navController)
        }
        composable("rates/{symbol}") { backStackEntry ->
            RatesAndConversion(
                currencyCode = backStackEntry.arguments?.getString("symbol") ?: "USD"
            )
        }
    }

    AlertDialog(messages = messageList.messages) {
        viewModel.clearMessages()
    }
}

@Composable
fun SupportedSymbols(
    navController: NavHostController,
    viewModel: CurrencyViewmodel = hiltViewModel()
) {
    val symbolsState = viewModel.symbols
    val filter = viewModel.filter

    LaunchedEffect(viewModel) {
        viewModel.refresh()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        FilterSearch()
        SupportedSymbolList(
            symbols = symbolsState,
            filter = filter
        ) {
            navController.navigate("rates/${it.key}")
        }
    }
}

@Composable
fun RatesAndConversion(
    viewModel: CurrencyViewmodel = hiltViewModel(),
    currencyCode: CurrencyCode
) {
    val scope = rememberCoroutineScope()
    var amount by remember { mutableStateOf("1") }
    var toCurrency by remember { mutableStateOf("") }

    fun getLatestRates() =
        CoroutineScope(Dispatchers.IO).launch {
            scope.launch { viewModel.applyFilter(toCurrency) }
            delay(3000)
            viewModel.clearConversions()
            viewModel.getLatestRates(
                base = currencyCode,
                listOf()
            )
        }

    Column {
        OutlinedTextField(
            singleLine = true,
            value = amount,
            label = { Text(stringResource(R.string.amount)) },
            onValueChange = {
                amount = it.toAmountOrOne()
                getLatestRates()
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        Column {
            OutlinedTextField(
                singleLine = true,
                value = toCurrency,
                label = { Text(stringResource(R.string.convert_to, currencyCode)) },
                onValueChange = {
                    toCurrency = it.toCurrencyCode()
                    getLatestRates()
                },
                enabled = true,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
        ConvertedCurrencyList(
            fromCurrency = currencyCode,
            amount = amount.toDouble()
        )
    }
}

@Composable
fun FilterSearch(
    viewModel: CurrencyViewmodel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    var query by remember { mutableStateOf("") }
    OutlinedTextField(
        singleLine = true,
        value = query,
        label = { Text(stringResource(R.string.currency_code)) },
        placeholder = { Text(stringResource(R.string.eur)) },
        onValueChange = {
            query = it.toCurrencyCode()
            CoroutineScope(Dispatchers.IO).launch {
                delay(500)
                scope.launch { viewModel.applyFilter(query) }
            }
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                Button(
                    onClick = {
                        query = ""
                        viewModel.clearFilter()
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Image(
                        painterResource(android.R.drawable.ic_menu_close_clear_cancel),
                        contentDescription = stringResource(R.string.clear_currency_button)
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun SupportedSymbolList(
    symbols: CurrencyViewmodel.SymbolsState,
    rates: CurrencyViewmodel.RatesState = CurrencyViewmodel.RatesState(),
    filter: String = "",
    onClick: (Symbol) -> Unit = {}
) {
    LaunchedEffect(symbols, rates) {

    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .semantics { testTag = "symbolList" }
    ) {
        symbols.symbolList
            .filter { it.key.contains(filter) || it.value.uppercase().contains(filter) }
            .forEach { symbol ->
                SymbolListItem(
                    symbol = symbol,
                    rate = rates.rates?.rates?.get(symbol.key)
                ) { onClick(it) }
            }
        Spacer(Modifier.height(500.dp))
    }
}

@Composable
fun SymbolListItem(
    symbol: Symbol,
    rate: Amount?,
    onClick: (Symbol) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { onClick(symbol) }
    ) {
        val moneySign = symbol.key.getDisplayCurrencySign()
        Text(
            buildAnnotatedString {
                rate?.let {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(symbol.key)
                    }
                    append(" | ")
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Medium
                        )
                    ) {
                        append("$moneySign${it.getDisplayAmount()}")
                    }
                } ?: run {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(symbol.key)
                    }
                    append(" | ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                        append(symbol.value)
                    }
                    append(" | ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        append(moneySign)
                    }
                }
            }
        )
    }
}

@Composable
fun ConvertedCurrencyList(
    viewModel: CurrencyViewmodel = hiltViewModel(),
    fromCurrency: CurrencyCode,
    amount: Double
) {
    val symbolsState = viewModel.symbols
    val ratesState = viewModel.rates
    val filter = viewModel.filter
    val convertState = viewModel.convertResult

    LaunchedEffect(viewModel) {
        viewModel.refresh()
    }

    if (convertState.isValid) {
        val result = convertState.convertResult!!
        val moneySign = result.query.to.getDisplayCurrencySign()
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(result.query.to)
                }
                append(" | ")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Medium
                    )
                ) {
                    append("$moneySign${result.result.getDisplayAmount()}")
                }
            },
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth()
        )
    } else {
        SupportedSymbolList(
            symbols = symbolsState,
            rates = ratesState,
            filter = filter
        ) {
            viewModel.convert(
                from = fromCurrency,
                to = it.key,
                amount = amount
            )
        }
    }
}

@Composable
fun AlertDialog(
    title: String = stringResource(R.string.uh_oh),
    messages: List<String>,
    onDismiss: () -> Unit
) {
    if (messages.isEmpty()) return
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Spacer(Modifier.height(16.dp))
            Text(
                text = title,
                textAlign = TextAlign.Left,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
            messages.forEach {
                Text(text = it)
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = { onDismiss() }) {
                Text(text = stringResource(R.string.dismiss))
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CurrencyExchangeTheme {
        Root()
    }
}
