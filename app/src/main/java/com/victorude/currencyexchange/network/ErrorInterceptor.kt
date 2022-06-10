package com.victorude.currencyexchange.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        // handle error based on response.code()
        return chain.proceed(request)
    }
}