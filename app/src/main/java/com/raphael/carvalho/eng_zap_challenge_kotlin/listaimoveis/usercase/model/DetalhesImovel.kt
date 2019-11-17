package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model

import java.math.BigDecimal

data class DetalhesImovel(
    val images: List<String>,
    val cidade: String,
    val bairro: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal
)