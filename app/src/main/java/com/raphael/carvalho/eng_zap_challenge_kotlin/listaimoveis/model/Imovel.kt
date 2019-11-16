package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.model

data class Imovel(
    val address: Address,
    val bathrooms: Int,
    val bedrooms: Int,
    val createdAt: String,
    val id: String,
    val images: List<String>,
    val listingStatus: String,
    val listingType: String,
    val owner: Boolean,
    val parkingSpaces: Int,
    val pricingInfos: PricingInfos,
    val updatedAt: String,
    val usableAreas: Int
)