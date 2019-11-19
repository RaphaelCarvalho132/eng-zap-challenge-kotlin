package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.repository.model

import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.ListaImoveisElegivel
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.DetalhesImovel
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.TipoNegocio
import java.math.BigDecimal

/**
 * Informacoes gerais do imovel
 */
data class ImovelVO(
    val address: AddressVO,
    val bathrooms: Int,
    val bedrooms: Int,
    val createdAt: String,
    val id: String,
    val images: List<String>,
    val listingStatus: String,
    val listingType: String,
    val owner: Boolean,
    val parkingSpaces: Int,
    val pricingInfos: PricingInfosVO,
    val updatedAt: String,
    val usableAreas: Int
)

/**
 * Retorna o valor por M2 do ImovelVO
 * @throws ArithmeticException quando [ImovelVO.usableAreas] == 0
 */
val ImovelVO.valorM2: BigDecimal
    get() = BigDecimal(pricingInfos.price) / BigDecimal(usableAreas)

fun ImovelVO.toImove(listaImoveisElegivel: ListaImoveisElegivel) =
    listaImoveisElegivel.criarImovel(
        TipoNegocio.getTipoNegocio(pricingInfos.businessType),
        pricingInfos.price,
        pricingInfos.period,
        bedrooms,
        bathrooms,
        parkingSpaces,
        usableAreas,
        DetalhesImovel(
            images,
            address.city,
            address.neighborhood,
            address.geoLocation.location.lat,
            address.geoLocation.location.lon
        )
    )