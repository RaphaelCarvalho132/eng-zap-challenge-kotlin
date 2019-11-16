package com.raphael.carvalho.eng_zap_challenge_kotlin

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

/**
 * Builder do retrofit que faz a configuracao inicial do objeto
 */
object RetrofitBuilder {
    operator fun invoke(
        apiURL: String = "http://grupozap-code-challenge.s3-website-us-east-1.amazonaws.com",
        httpClient: OkHttpClient = okHttpClient
    ) = with(Retrofit.Builder()) {
        baseUrl(apiURL)
        client(httpClient)
        addConverterFactory(GsonConverterFactory.create())
        build()
    }

    private val okHttpClient: OkHttpClient
        get() {
            return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        }

    private val logging: HttpLoggingInterceptor
        get() {
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

            return logging
        }

    inline fun <reified T> new() = invoke().create(T::class.java)
}