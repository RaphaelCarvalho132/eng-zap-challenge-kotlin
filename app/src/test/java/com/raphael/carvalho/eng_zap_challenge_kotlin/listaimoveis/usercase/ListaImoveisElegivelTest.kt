package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase

import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.model.*
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.model.PricingInfosVO.BusinessType.ALUGUEL
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.model.PricingInfosVO.BusinessType.VENDA
import com.raphael.carvalho.eng_zap_challenge_kotlin.util.InfoTeste
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import java.math.BigDecimal

/**
 * Testa regras de elegibilidade
 */
class ListaImoveisElegivelTest {
    /**
     * teste de regras de elegibilidade do ZAP imoveis
     */
    @TestFactory
    fun `ListaZAP_ehElegivel - valida elegibilidade de imoveis`() =
        listOf(
            InfoTeste(
                "atende as regras de elegibilidade",
                true,
                criarImoveElegivelZap()
            ), InfoTeste(
                "possui latitude e longitude igual 0",
                false,
                criarImoveElegivelZap(lat = BigDecimal.ZERO, lon = BigDecimal.ZERO)
            ), InfoTeste(
                "nao esteja a venda",
                false,
                criarImoveElegivelZap(ALUGUEL)
            ), InfoTeste(
                "nao possui uma area usavel",
                false,
                criarImoveElegivelZap(usableAreas = 0)
            ), InfoTeste(
                "possui o valor por M2 <= R$ 3.500,00",
                false,
                criarImoveElegivelZap(price = "7000")
            )
        ).mapIndexed { index, info ->
            dynamicTest(
                "[$index]: ao receber imovel que \"${info.descricaoTeste}\", " +
                        "deve se ter estado: \"${if (info.resultado) "elegivel" else "ilegivel"}\""
            ) {
                assertEquals(info.resultado, ListaZAPElegivel.ehElegivel(info.valor))
            }
        }

    /**
     * teste de regras de elegibilidade do Viva Real
     */
    @TestFactory
    fun `ListaVivaReal_ehElegivel - valida elegibilidade de imoveis`() =
        listOf(
            InfoTeste(
                "atende as regras de elegibilidade",
                true,
                criarImoveElegivelVivaReal()
            ), InfoTeste(
                "possui latitude e longitude igual 0",
                false,
                criarImoveElegivelVivaReal(lat = BigDecimal.ZERO, lon = BigDecimal.ZERO)
            ), InfoTeste(
                "nao esteja a alugando",
                false,
                criarImoveElegivelVivaReal(VENDA)
            ), InfoTeste(
                "nao possui um condominio valido",
                false,
                criarImoveElegivelVivaReal(monthlyCondoFee = "")
            ), InfoTeste(
                "nao possui um valor de condominio valido",
                false,
                criarImoveElegivelVivaReal(monthlyCondoFee = "-0.1")
            ), InfoTeste(
                "possui o valor de condomino >= a 30% do valor do aluguel",
                false,
                criarImoveElegivelVivaReal(monthlyCondoFee = "30")
            )
        ).mapIndexed { index, info ->
            dynamicTest(
                "[$index]: ao receber imovel que \"${info.descricaoTeste}\", " +
                        "deve se ter estado: \"${if (info.resultado) "elegivel" else "ilegivel"}\""
            ) {
                assertEquals(info.resultado, ListaVivaRealElegivel.ehElegivel(info.valor))
            }
        }

    private fun criarImoveElegivelZap(
        tipoNegocio: String = VENDA,
        lat: BigDecimal = BigDecimal.ONE,
        lon: BigDecimal = BigDecimal.ONE,
        price: String = "7000.02",
        usableAreas: Int = 2
    ) = criarImoveElegivel(tipoNegocio, lat, lon, price, usableAreas, "")

    private fun criarImoveElegivelVivaReal(
        tipoNegocio: String = ALUGUEL,
        lat: BigDecimal = BigDecimal.ONE,
        lon: BigDecimal = BigDecimal.ONE,
        price: String = "100",
        monthlyCondoFee: String = "29"
    ) = criarImoveElegivel(tipoNegocio, lat, lon, price, 0, monthlyCondoFee)

    private fun criarImoveElegivel(
        tipoNegocio: String,
        lat: BigDecimal,
        lon: BigDecimal,
        price: String,
        usableAreas: Int,
        monthlyCondoFee: String
    ): ImovelVO {
        val imovel = mockk<ImovelVO>()
        every { imovel.address } returns criarAddressElegivel(lat, lon)
        every { imovel.pricingInfos } returns criarPricingInfos(tipoNegocio, price, monthlyCondoFee)
        every { imovel.usableAreas } returns usableAreas

        return imovel
    }

    private fun criarAddressElegivel(lat: BigDecimal, lon: BigDecimal): AddressVO {
        val location = mockk<LocationVO>()
        every { location.lat } returns lat
        every { location.lon } returns lon

        val geoLocation = mockk<GeoLocationVO>()
        every { geoLocation.location } returns location

        val address = mockk<AddressVO>()
        every { address.geoLocation } returns geoLocation

        return address
    }

    private fun criarPricingInfos(
        tipoNegocio: String,
        price: String,
        monthlyCondoFee: String
    ): PricingInfosVO {
        val pricingInfos = mockk<PricingInfosVO>()
        every { pricingInfos.businessType } returns tipoNegocio
        every { pricingInfos.price } returns price
        every { pricingInfos.monthlyCondoFee } returns monthlyCondoFee

        return pricingInfos
    }
}