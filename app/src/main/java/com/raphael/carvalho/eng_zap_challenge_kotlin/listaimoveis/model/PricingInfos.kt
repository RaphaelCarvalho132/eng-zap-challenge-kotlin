package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.model

/**
 * Informacoes de preco do imovel
 */
data class PricingInfos(
    val businessType: String,
    val monthlyCondoFee: String,
    val price: String,
    val yearlyIptu: String
)