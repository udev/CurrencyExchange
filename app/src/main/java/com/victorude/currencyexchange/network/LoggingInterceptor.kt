package com.victorude.currencyexchange.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class LoggingInterceptor : Interceptor {
    companion object {
        const val TAG = "LoggingInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val t1 = System.nanoTime()
        Log.i(
            TAG, "Sending request ${request.url()} on ${chain.connection()}" +
                    "\n${request.headers()}"
        )
        val response: Response = chain.proceed(request)
        val t2 = System.nanoTime()
        Log.i(
            TAG, "Received response for ${response.request().url()}" +
                    " in ${(t2 - t1) / 1e6} ${response.headers()}"
        )
        return response
    }
}