package com.example.transactions.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.Base64
import java.util.concurrent.TimeUnit

class ServiceRest {

    fun getClient(url: String): Retrofit? = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getOkClient())
        .build()

    private fun getOkClient() = OkHttpClient().newBuilder()
        .connectTimeout(40, TimeUnit.SECONDS)
        .readTimeout(40, TimeUnit.SECONDS)
        .writeTimeout(40, TimeUnit.SECONDS)
        .addInterceptor(getLoginInterceptor())
        .build()

    private fun getLoginInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
    }

    fun getClientWithAuthHeader(
        url: String,
        commerceCode: String,
        terminalCode: String
    ): Retrofit? {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestWithHeader = originalRequest.newBuilder()
                    .header("Authorization", "Basic MDAwMTIzMDAwQUJD")
                    .header("Content-Type", "application/json")
                    .method(originalRequest.method(), originalRequest.body())
                    .build()
                chain.proceed(requestWithHeader)
            }
            .addInterceptor(getLoginInterceptor())
            .addInterceptor(getBodyInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://localhost:8080/api/payments/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    private fun getBodyInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun generateBasicAuthHeader(commerceCode: String, terminalCode: String): String {
        // Combinar commerceCode y terminalCode en un solo String
        val combinedString = "$commerceCode:$terminalCode"

        // Codificar en BASE64
        val encodedBytes =
            Base64.getEncoder().encode(combinedString.toByteArray(StandardCharsets.UTF_8))
        val encodedString = String(encodedBytes, StandardCharsets.UTF_8)

        // Crear el encabezado Authorization
        return "Basic $encodedString"
    }


}