package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.model

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.math.BigDecimal

/**
 * Testa a extension em Imovel
 */
class ImovelKtTest {
    /**
     * Testa erro ao possuir uma area usavel igual a 0
     */
    @Test
    fun `getValorM2 - ao possuir uma area usavel 0, deve retornar uma exception`() {
        assertThrows(ArithmeticException::class.java) {
            criarImove("999.99", 0).valorM2
        }
    }

    /**
     * testa o calculo do valor por M2
     */
    @TestFactory
    fun `Imovel_getValorM2 - valida calculo valor M2`() =
        listOf(
            criarImove("200", 2) to BigDecimal("100"),
            criarImove("999.99", 3) to BigDecimal("333.33")
        ).mapIndexed { index, info ->
            DynamicTest.dynamicTest(
                "[$index]: ao possuir um valor de ${info.first.pricingInfos.price}" +
                        " e uma area utilizavel de ${info.first.usableAreas}, " +
                        "deve retornar o valor por M2 de: \"${info.second}\""
            ) {
                assertTrue(info.second.compareTo(info.first.valorM2) == 0)
            }
        }

    private fun criarImove(price: String, usableAreas: Int): Imovel {
        val pricingInfos = mockk<PricingInfos>()
        every { pricingInfos.price } returns price

        val imovel = mockk<Imovel>()
        every { imovel.pricingInfos } returns pricingInfos
        every { imovel.usableAreas } returns usableAreas

        return imovel
    }
}