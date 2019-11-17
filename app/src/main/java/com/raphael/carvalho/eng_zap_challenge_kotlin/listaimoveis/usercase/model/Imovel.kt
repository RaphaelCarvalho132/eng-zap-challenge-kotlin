package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model

data class Imovel(
    private val tipoNegocio: String,
    private val valor: String,
    private val periodo: String?,
    val qtdQuartos: Int,
    val qtdBanheiros: Int,
    val qtdVagas: Int,
    val areaM2: Int,
    val detalhesImovel: DetalhesImovel
)