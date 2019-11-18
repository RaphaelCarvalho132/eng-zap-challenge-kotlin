package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.viewmodel

import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.Imovel

sealed class ListaImoveisEstados {
    object Carregamento : ListaImoveisEstados()
    object CarregamentoPaginacao : ListaImoveisEstados()
    object ErroCarregamento : ListaImoveisEstados()
    data class ExibirInformacoes(
        val imoveisZAP: List<Imovel>?,
        val imoveisVivaReal: List<Imovel>?
    ) : ListaImoveisEstados()
}