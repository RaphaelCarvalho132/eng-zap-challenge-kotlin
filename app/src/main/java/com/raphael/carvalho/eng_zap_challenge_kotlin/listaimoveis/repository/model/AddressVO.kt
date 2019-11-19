package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.repository.model

/**
 * Endereco do imovel
 */
data class AddressVO(
    val city: String,
    val geoLocation: GeoLocationVO,
    val neighborhood: String
)