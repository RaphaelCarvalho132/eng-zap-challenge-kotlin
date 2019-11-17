package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model

data class Imovel(
    private val tipoNegocio: String,
    private val valor: String,
    private val periodo: String?,
    val qtdQuartos: Int,
    val qtdBanheiros: Int,
    val qtdVagas: Int,
    val areaM2: Int,
    val detalhesImovel: DetalhesImovel
) {
    val arredoresGrupoZAP: Boolean
        get() {
            TODO(
                """IMPLEMENTAR REGRAS
            |ZAP:
            |Quando o imóvel estiver dentro do bounding box dos arredores do Grupo ZAP (descrito abaixo) considere a regra de valor mínimo (do imóvel) 10% menor.
            |Viva Real:
            |Quando o imóvel estiver dentro do bounding box dos arredores do Grupo ZAP (descrito abaixo) considere a regra de valor máximo (do aluguel do imóvel) 50% maior.
        """.trimMargin()
            )
        }
}