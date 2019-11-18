package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase

import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.repository.model.ImovelVO
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.repository.model.PricingInfosVO
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.repository.model.PricingInfosVO.BusinessType.ALUGUEL
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.repository.model.PricingInfosVO.BusinessType.VENDA
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.repository.model.valorM2
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.DetalhesImovel
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.Imovel
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.ImovelVivaReal
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.ImovelZAP
import java.math.BigDecimal

/**
 * Classe que lista os imoveis elegiveis de uma forma generica
 */
sealed class ListaImoveisElegivel {
    /**
     * Regra geral de elegibilidade
     */
    open fun ehElegivel(imovel: ImovelVO): Boolean {
        val location = imovel.address.geoLocation.location

        val latitudeEh0 = BigDecimal.ZERO.compareTo(location.lat) == 0
        val longitudeEh0 = BigDecimal.ZERO.compareTo(location.lon) == 0

        return !(latitudeEh0 && longitudeEh0)
    }

    abstract fun criarImovel(
        tipoNegocio: String,
        valor: String,
        periodo: String?,
        qtdQuartos: Int,
        qtdBanheiros: Int,
        qtdVagas: Int,
        areaM2: Int,
        detalhesImovel: DetalhesImovel
    ): Imovel
}

/**
 * Classe que lista os imoveis elegiveis pela ZAP imoveis
 */
object ListaZAPElegivel : ListaImoveisElegivel() {
    private val menorValorM2NaoIncluso = BigDecimal("3500")

    /**
     * Regra da ZAP de elegibilidade
     */
    override fun ehElegivel(imovel: ImovelVO): Boolean {
        return super.ehElegivel(imovel) &&
                imovel.estaAVenda() &&
                imovel.possuiValorM2Elegivel()
    }

    private fun ImovelVO.estaAVenda() = VENDA == pricingInfos.businessType

    private fun ImovelVO.possuiValorM2Elegivel(): Boolean {
        return usableAreas != 0 &&
                valorM2 > menorValorM2NaoIncluso
    }

    override fun criarImovel(
        tipoNegocio: String,
        valor: String,
        periodo: String?,
        qtdQuartos: Int,
        qtdBanheiros: Int,
        qtdVagas: Int,
        areaM2: Int,
        detalhesImovel: DetalhesImovel
    ) = ImovelZAP(
        tipoNegocio,
        valor,
        periodo,
        qtdQuartos,
        qtdBanheiros,
        qtdVagas,
        areaM2,
        detalhesImovel
    )
}

/**
 * Classe que lista os imoveis elegiveis pela Viva Real
 */
object ListaVivaRealElegivel : ListaImoveisElegivel() {
    // Porcentagem do valor do alguel maximo, nao incluso,
    // em relacao ao valor do condominio elegivel
    private val _30PorCento = BigDecimal("0.3")

    /**
     * Regra da Viva Real de elegibilidade
     */
    override fun ehElegivel(imovel: ImovelVO): Boolean {
        return super.ehElegivel(imovel) &&
                imovel.estaAlugando() &&
                imovel.pricingInfos.possuiCondominioElegivel()
    }

    private fun ImovelVO.estaAlugando() = ALUGUEL == pricingInfos.businessType

    private fun PricingInfosVO.possuiCondominioElegivel(): Boolean {
        return monthlyCondoFee
            .toBigDecimalOrNull()
            ?.let { valorCondominio ->
                val valorMaxCondominoNaoIncluso =
                    price.toBigDecimal() * _30PorCento

                valorCondominio >= BigDecimal.ZERO &&
                        valorCondominio < valorMaxCondominoNaoIncluso
            } ?: false
    }

    override fun criarImovel(
        tipoNegocio: String,
        valor: String,
        periodo: String?,
        qtdQuartos: Int,
        qtdBanheiros: Int,
        qtdVagas: Int,
        areaM2: Int,
        detalhesImovel: DetalhesImovel
    ) = ImovelVivaReal(
        tipoNegocio,
        valor,
        periodo,
        qtdQuartos,
        qtdBanheiros,
        qtdVagas,
        areaM2,
        detalhesImovel
    )
}