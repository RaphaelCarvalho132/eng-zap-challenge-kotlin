package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.model

/**
 * Informacoes de localizacao do imovel
 */
data class GeoLocationVO(
    val location: LocationVO,
    val precision: String
)