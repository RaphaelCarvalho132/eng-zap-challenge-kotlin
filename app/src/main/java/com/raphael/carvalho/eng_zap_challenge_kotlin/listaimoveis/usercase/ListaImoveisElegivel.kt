package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase

import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.model.ImovelVO
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.model.PricingInfosVO
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.model.PricingInfosVO.BusinessType.ALUGUEL
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.model.PricingInfosVO.BusinessType.VENDA
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.model.valorM2
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
}