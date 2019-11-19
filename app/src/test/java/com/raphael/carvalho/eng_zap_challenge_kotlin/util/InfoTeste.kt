package com.raphael.carvalho.eng_zap_challenge_kotlin.util

/**
 * Usado par a encapsular informacoes para testes parametrizados que precisam de descricao
 * por serem testes mais complexos
 */
data class InfoTeste<RESULTADO, VALOR_TESTE>(
    val descricaoTeste: String,
    val resultado: RESULTADO,
    val valor: VALOR_TESTE
)