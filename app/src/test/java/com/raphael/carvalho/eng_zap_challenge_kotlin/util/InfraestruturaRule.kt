package com.raphael.carvalho.eng_zap_challenge_kotlin.util

import com.raphael.carvalho.eng_zap_challenge_kotlin.RetrofitBuilder
import io.mockk.every
import io.mockk.mockkObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.ExternalResource

class InfraestruturaRule : ExternalResource() {
    private lateinit var server: MockWebServer

    override fun before() {
        super.before()
        server = MockWebServer()

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .build()

        mockkObject(RetrofitBuilder)
        every { RetrofitBuilder.invoke() } returns RetrofitBuilder(
            server.url("/").toString(),
            httpClient
        )
    }

    override fun after() {
        server.shutdown()
        super.after()
    }

    fun adicionarRequisicao(statusCode: Int, nomeArquivoResources: String) {
        server.enqueue(
            MockResponse().apply {
                setResponseCode(statusCode)
                setBody(
                    javaClass
                        .classLoader!!
                        .getResourceAsStream(nomeArquivoResources)
                        .bufferedReader()
                        .use { it.readText() })
            }
        )
    }
}