package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.model

import java.math.BigDecimal

/**
 * informacoes de latitude e longitude do imovel
 */
data class Location(
    val lat: BigDecimal,
    val lon: BigDecimal
)