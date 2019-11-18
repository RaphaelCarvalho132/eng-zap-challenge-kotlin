package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model

import java.math.BigDecimal

/**
 * Atributos dos imoveis do viva real e da ZAP
 */
interface Imovel {
    val tipoNegocio: String
    val valorImovel: BigDecimal
    val qtdQuartos: Int
    val qtdBanheiros: Int
    val qtdVagas: Int
    val areaM2: Int
    val detalhesImovel: DetalhesImovel
}