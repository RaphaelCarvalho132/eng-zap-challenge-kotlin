package com.raphael.carvalho.eng_zap_challenge_kotlin.util

import com.raphael.carvalho.eng_zap_challenge_kotlin.RetrofitBuilder
import com.raphael.carvalho.eng_zap_challenge_kotlin.util.Resources.lerArquivo
import io.mockk.every
import io.mockk.mockkObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

/**
 * Rule para configurar o MockWebServer
 */
object InfraestruturaLifeCycleExtensions : BeforeAllCallback, AfterAllCallback {
    lateinit var server: MockWebServer

    override fun beforeAll(context: ExtensionContext?) {
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

    override fun afterAll(context: ExtensionContext?) {
        server.shutdown()
    }

    /**
     * Adicion na pilha a resposta, que e informada pelo [nomeArquivoResources], e o [statusCode]
     * que sera retornada pelo MockWebServer
     */
    fun adicionarRequisicao(statusCode: Int, nomeArquivoResources: String) {
        server.enqueue(
            MockResponse().apply {
                setResponseCode(statusCode)
                setBody(lerArquivo(nomeArquivoResources))
            }
        )
    }
}