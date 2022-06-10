package com.victorude.currencyexchange.model

import kotlinx.serialization.Serializable

@Serializable
data class FixerError(
    val code: Int,
    val type: String,
    val info: String
)
