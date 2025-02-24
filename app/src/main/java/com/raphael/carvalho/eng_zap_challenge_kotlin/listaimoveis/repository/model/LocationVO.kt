package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.repository.model

import java.math.BigDecimal

/**
 * informacoes de latitude e longitude do imovel
 */
data class LocationVO(
    val lat: BigDecimal,
    val lon: BigDecimal
)