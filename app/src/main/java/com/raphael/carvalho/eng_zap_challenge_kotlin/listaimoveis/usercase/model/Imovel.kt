package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model

import android.os.Parcelable
import androidx.annotation.StringRes
import com.raphael.carvalho.eng_zap_challenge_kotlin.R
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

/**
 * Atributos dos imoveis do viva real e da ZAP
 */
interface Imovel : Parcelable {
    @get:StringRes
    val nomeNegocio: Int
    val valorImovel: BigDecimal
    val qtdQuartos: Int
    val qtdBanheiros: Int
    val qtdVagas: Int
    val areaM2: Int
    val detalhesImovel: DetalhesImovel

    @StringRes
    fun getStringResTipoNegocio(tipoNegocio: TipoNegocio): Int {
        return when (tipoNegocio) {
            is TipoNegocio.Venda -> R.string.nome_negocio_a_venda
            is TipoNegocio.Aluguel -> R.string.nome_negocio_alugar
            is TipoNegocio.Desconhecido -> R.string.nome_negocio_desconhecido
        }
    }
}

sealed class TipoNegocio : Parcelable {
    companion object {
        fun getTipoNegocio(tipoNegocio: String) = when (tipoNegocio) {
            Venda.TIPO_NEGOCIO -> Venda
            Aluguel.TIPO_NEGOCIO -> Aluguel
            else -> Desconhecido
        }
    }

    @Parcelize
    object Venda : TipoNegocio() {
        const val TIPO_NEGOCIO = "SALE"
    }

    @Parcelize
    object Aluguel : TipoNegocio() {
        const val TIPO_NEGOCIO = "RENTAL"
    }

    @Parcelize
    object Desconhecido : TipoNegocio()
}