package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.model

data class Address(
    val city: String,
    val geoLocation: GeoLocation,
    val neighborhood: String
)