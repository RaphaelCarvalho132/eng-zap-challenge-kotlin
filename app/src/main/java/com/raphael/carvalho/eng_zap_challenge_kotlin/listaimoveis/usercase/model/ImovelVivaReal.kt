package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model

import androidx.annotation.StringRes
import java.math.BigDecimal

/**
 * Classe com regra do Viva Real
 */
data class ImovelVivaReal(
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
        val aumento50Porcento = "1.5".toBigDecimal()
    }

    @get:StringRes
    override val nomeNegocio: Int
        get() = getStringResTipoNegocio(tipoNegocio)

    override val valorImovel: BigDecimal
        get() {
            return if (detalhesImovel.arredoresGrupoZAP) {
                valor.toBigDecimal() * aumento50Porcento
            } else valor.toBigDecimal()
        }
}