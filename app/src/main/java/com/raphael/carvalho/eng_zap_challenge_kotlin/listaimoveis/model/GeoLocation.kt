package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.model

/**
 * Informacoes de localizacao do imovel
 */
data class GeoLocation(
    val location: Location,
    val precision: String
)