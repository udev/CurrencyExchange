package com.victorude.currencyexchange.common

import com.google.gson.Gson
import com.google.gson.JsonElement
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.time.Instant
import java.time.format.DateTimeParseException

class TimestampConverter : Converter<ResponseBody, Instant> {
    override fun convert(value: ResponseBody): Instant? {
        return Gson().fromJson(value.string(), JsonElement::class.java)?.asJsonObject?.let {
            val longTime = it.get("timestamp").asLong
            try {
                Instant.parse(longTime.toString())
            } catch (e: DateTimeParseException) {
                e.printStackTrace()
                Instant.now()
            }
        } ?: run {
            Instant.now()
        }
    }
}

class TimestampConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, Instant> {
        return TimestampConverter()
    }
}