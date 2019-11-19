package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model

import androidx.annotation.StringRes
import java.math.BigDecimal

/**
 * Classe com regra do ZAP
 */
data class ImovelZAP(
    private val tipoNegocio: TipoNegocio,
    private val valor: String,
    private val periodo: String?,
    override val qtdQuartos: Int,
    override val qtdBanheiros: Int,
    override val qtdVagas: Int,
    override val areaM2: Int,
    override val detalhesImovel: DetalhesImovel
) : Imovel {
    companion object {
        val desconto10Porcento = "0.9".toBigDecimal()
    }

    @get:StringRes
    override val nomeNegocio: Int
        get() = getStringResTipoNegocio(tipoNegocio)

    override val valorImovel: BigDecimal
        get() {
            return if (detalhesImovel.arredoresGrupoZAP) {
                valor.toBigDecimal() * desconto10Porcento
            } else valor.toBigDecimal()
        }
}