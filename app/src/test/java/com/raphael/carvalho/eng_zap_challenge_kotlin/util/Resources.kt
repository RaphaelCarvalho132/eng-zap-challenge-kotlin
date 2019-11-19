package com.raphael.carvalho.eng_zap_challenge_kotlin.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Classe util para acessar arquivos nos resources
 */
object Resources {
    /**
     * Le o arquivo informado [nomeArquivoResources] e transforma em uma string
     */
    fun lerArquivo(nomeArquivoResources: String): String {
        return javaClass
            .classLoader!!
            .getResourceAsStream(nomeArquivoResources)
            .bufferedReader()
            .use { it.readText() }
    }

    /**
     * Le o arquivo informado [nomeArquivoResources] e transforma em um objeto
     */
    inline fun <reified T> lerArquivo(nomeArquivoResources: String): T {
        return Gson().fromJson(
            lerArquivo(nomeArquivoResources),
            object : TypeToken<T>() {}.type
        )
    }
}