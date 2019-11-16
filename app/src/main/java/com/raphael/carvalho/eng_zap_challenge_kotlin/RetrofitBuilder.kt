package com.raphael.carvalho.eng_zap_challenge_kotlin

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

object RetrofitBuilder {
    operator fun invoke(
        apiURL: String = "http://grupozap-code-challenge.s3-website-us-east-1.amazonaws.com",
        httpClient: OkHttpClient = criarClienteDefault()
    ) = with(Retrofit.Builder()) {
        baseUrl(apiURL)
        client(httpClient)
        addConverterFactory(GsonConverterFactory.create())
        build()
    }

    inline fun <reified T> new() = invoke().create(T::class.java)

    private fun criarClienteDefault(): OkHttpClient {
        val logging = HttpLoggingInterceptor(
            object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Timber.tag("OkHttp").d(message)
                }
            }
        ).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // Esconde possiveis informacoes sensiveis
        logging.redactHeader("Authorization")
        logging.redactHeader("Cookie")

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
}