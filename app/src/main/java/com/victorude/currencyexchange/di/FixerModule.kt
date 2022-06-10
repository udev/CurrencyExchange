package com.victorude.currencyexchange.di

import android.content.Context
import com.victorude.currencyexchange.BuildConfig
import com.victorude.currencyexchange.common.TimestampConverterFactory
import com.victorude.currencyexchange.network.ErrorInterceptor
import com.victorude.currencyexchange.network.FixerRepository
import com.victorude.currencyexchange.network.FixerService
import com.victorude.currencyexchange.network.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object FixerModule {
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val clientRequest = originalRequest.newBuilder()
                    .addHeader("apikey", BuildConfig.FIXER_API_KEY)
                    .build()
                chain.proceed(clientRequest)
            }
            .addInterceptor(LoggingInterceptor())
            .addInterceptor(ErrorInterceptor(context))
            .build()
    }

    @Provides
    fun provideFixerService(
        okHttpClient: OkHttpClient
    ): FixerService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(TimestampConverterFactory())
            .build()
            .create(FixerService::class.java)
    }

    @Provides
    fun provideFixerRepository(
        fixerService: FixerService
    ): FixerRepository {
        return FixerRepository(fixerService)
    }
}