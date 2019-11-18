package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

/**
 * Teste das regras do [ImovelVivaReal]
 */
internal class ImovelVivaRealTest {
    /**
     * teste de regras de elegibilidade do Viva Real
     */
    @TestFactory
    fun `valorImovel - valida se a latitude e longitude esta nos arredores do grupo ZAP`() =
        listOf(
            criarImovel(arredoresGrupoZAP = false) to "1000".toBigDecimal(),
            criarImovel(arredoresGrupoZAP = true) to "1500".toBigDecimal()
        ).mapIndexed { index, info ->
            val arredoresZAP = info.first.detalhesImovel.arredoresGrupoZAP
            DynamicTest.dynamicTest(
                "[$index]: ao receber valor de imovel de \"${info.first.valorImovel}\"" +
                        " \" ${if (arredoresZAP) "estando" else "nao estando"}\"" +
                        " nos arredores grupo ZAP, deve ser cobrado o valor de \"${info.second}\""
            ) {
                assertTrue(info.second.compareTo(info.first.valorImovel) == 0)
            }
        }

    private fun criarImovel(
        valor: String = "1000",
        arredoresGrupoZAP: Boolean
    ): Imovel {
        val detalhesImovel = mockk<DetalhesImovel>()
        every { detalhesImovel.arredoresGrupoZAP } returns arredoresGrupoZAP

        return ImovelVivaReal(
            "",
            valor,
            null,
            0,
            0,
            0,
            0,
            detalhesImovel
        )
    }
}