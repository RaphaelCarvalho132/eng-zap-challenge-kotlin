package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.repository.model

/**
 * Informacoes de preco do imovel
 */
data class PricingInfosVO(
    val businessType: String,
    val monthlyCondoFee: String,
    val price: String,
    val yearlyIptu: String,
    val period: String? = null,
    val rentalTotalPrice: String? = null
) {
    /**
     * Informacoes sobre o enum da variavel [businessType]
     */
    companion object BusinessType {
        const val VENDA = "SALE"
        const val ALUGUEL = "RENTAL"
    }
}