package com.victorude.currencyexchange.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class FixerHttpError(
    val message: String
)