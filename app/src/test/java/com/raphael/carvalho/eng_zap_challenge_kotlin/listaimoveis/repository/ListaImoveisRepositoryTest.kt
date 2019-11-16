package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.repository

import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.model.*
import com.raphael.carvalho.eng_zap_challenge_kotlin.util.InfraestruturaRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal

class ListaImoveisRepositoryTest {
    @get:Rule
    val rule = InfraestruturaRule()
    private lateinit var repository: ListaImoveisRepository

    @Before
    fun `configurar antes de cada teste`() {
        repository = ListaImoveisRepository()
    }

    @Test
    fun listarImoveis() {
        rule.adicionarRequisicao(
            200,
            "listarImoveis/respostaSimplificada.json"
        )

        val imoveis = runBlocking {
            repository.listarImoveis(1)
        }

        Assert.assertEquals(
            listarImoveisRespostaSimplificada(),
            imoveis
        )
    }

    private fun listarImoveisRespostaSimplificada() = listOf(
        Imovel(
            criarAddressRespostaSimplificada(),
            5,
            4,
            "2018-03-24T11:06:37.238Z",
            "53ad37eb6177",
            listarImagensRespostaSimplificada(),
            "ACTIVE",
            "USED",
            false,
            4,
            criarPricingInfosRespostaSimplificada(),
            "2018-03-25T11:06:37.238Z",
            360
        )
    )

    private fun criarAddressRespostaSimplificada() = Address(
        "São Paulo",
        GeoLocation(
            Location(
                lat = BigDecimal("-23.606269"),
                lon = BigDecimal("-46.70559")
            ),
            "ROOFTOP"
        ),
        "Real Parque"
    )

    private fun listarImagensRespostaSimplificada() = listOf(
        "http://grupozap-code-challenge.s3-website-us-east-1.amazonaws.com/images/pic8.jpg",
        "http://grupozap-code-challenge.s3-website-us-east-1.amazonaws.com/images/pic10.jpg",
        "http://grupozap-code-challenge.s3-website-us-east-1.amazonaws.com/images/pic17.jpg",
        "http://grupozap-code-challenge.s3-website-us-east-1.amazonaws.com/images/pic14.jpg",
        "http://grupozap-code-challenge.s3-website-us-east-1.amazonaws.com/images/pic15.jpg"
    )

    private fun criarPricingInfosRespostaSimplificada() = PricingInfos(
        "SALE",
        "4100",
        "2200000",
        "1500"
    )
}