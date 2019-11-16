package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.model

/**
 * Endereco do imovel
 */
data class Address(
    val city: String,
    val geoLocation: GeoLocation,
    val neighborhood: String
)