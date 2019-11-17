package com.raphael.carvalho.eng_zap_challenge_kotlin.util

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
}