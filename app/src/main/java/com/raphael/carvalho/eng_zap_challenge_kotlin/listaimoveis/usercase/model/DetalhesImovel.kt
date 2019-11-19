package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

/**
 * Detalhes a mais sobre o imovel
 */
@Parcelize
data class DetalhesImovel(
    val images: List<String>,
    val cidade: String,
    val bairro: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal
) : Parcelable {
    companion object ArredoresGrupoZap {
        val minLon = "-46.693419".toBigDecimal()
        val minLat = "-23.568704".toBigDecimal()
        val maxLon = "-46.641146".toBigDecimal()
        val maxLat = "-23.546686".toBigDecimal()
    }

    val arredoresGrupoZAP: Boolean
        get() {
            return longitude in minLon..maxLon &&
                    latitude in minLat..maxLat
        }
}