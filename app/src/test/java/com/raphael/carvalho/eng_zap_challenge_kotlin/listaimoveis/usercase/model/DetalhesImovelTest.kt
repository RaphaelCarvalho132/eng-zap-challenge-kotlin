package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model

import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.DetalhesImovel.ArredoresGrupoZap.maxLat
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.DetalhesImovel.ArredoresGrupoZap.maxLon
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.DetalhesImovel.ArredoresGrupoZap.minLat
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.DetalhesImovel.ArredoresGrupoZap.minLon
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.math.BigDecimal
import java.math.BigDecimal.ONE

/**
 * Teste das regras do [DetalhesImovel]
 */
internal class DetalhesImovelTest {
    /**
     * teste de regras de elegibilidade do Viva Real
     */
    @TestFactory
    fun `getArredoresGrupoZAP - valida se a latitude e longitude esta nos arredores do grupo ZAP`() =
        listOf(
            criarDetalhesImovel(maxLat, maxLon) to true,
            criarDetalhesImovel(minLat, minLon) to true,
            criarDetalhesImovel(maxLat + ONE, maxLon) to false,
            criarDetalhesImovel(maxLat, maxLon + ONE) to false,
            criarDetalhesImovel(minLat - ONE, minLon) to false,
            criarDetalhesImovel(minLat, minLon - ONE) to false
        ).mapIndexed { index, info ->
            DynamicTest.dynamicTest(
                "[$index]: ao receber latitude(\"${info.first.latitude}\")" +
                        " e longitude(\"${info.first.longitude}\")," +
                        " deve informar que esta \"${if (info.second) "dentro" else "fora"}\"" +
                        " arredores do grupo ZAP"
            ) {
                assertEquals(info.second, info.first.arredoresGrupoZAP)
            }
        }

    private fun criarDetalhesImovel(
        latitude: BigDecimal,
        longitude: BigDecimal
    ) = DetalhesImovel(
        listOf(),
        "Sao Paulo",
        "Vila Mariana",
        latitude,
        longitude
    )
}