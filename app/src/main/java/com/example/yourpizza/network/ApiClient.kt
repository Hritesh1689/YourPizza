package com.example.yourpizza.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private var retrofit: Retrofit? = null
    private var baseUrl: String = "https://625bbd9d50128c570206e502.mockapi.io/api/"

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getLoggingClientBuilder().build())
                .build()
        }
        return retrofit
    }

    private fun getLoggingClientBuilder(): OkHttpClient.Builder {
         val okHttpClientBuilder= OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS) // Set the connection timeout value here
            .readTimeout(10, TimeUnit.SECONDS) // Set the read timeout value if needed
            .writeTimeout(10, TimeUnit.SECONDS);
        return okHttpClientBuilder
    }
}