package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase

import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.repository.ListaImoveisRepository
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.repository.model.ImovelVO
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.repository.model.toImove
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.Imovel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

/**
 * Caso de uso da listagem de imoveis
 */
class ListaImoveisUseCase(
    val repository: ListaImoveisRepository = ListaImoveisRepository()
) {
    private var paginaAtual = 1

    suspend fun listarImoveis(): Map<ListaImoveisElegivel, List<Imovel>> {
        paginaAtual = 1
        return filtrarImoveisElegiveis(repository.listarImoveis(paginaAtual))
    }

    suspend fun listarImoveisProximaPagina(): Map<ListaImoveisElegivel, List<Imovel>> {
        return filtrarImoveisElegiveis(repository.listarImoveis(++paginaAtual))
    }

    private suspend fun filtrarImoveisElegiveis(imoveis: List<ImovelVO>) =
        runBlocking {
            val imoveisZAPElegiveis =
                filtrarImoveisElegiveis(ListaZAPElegivel, imoveis)
            val imoveisVivaRealElegiveis =
                filtrarImoveisElegiveis(ListaVivaRealElegivel, imoveis)

            mapOf(
                imoveisZAPElegiveis.await(),
                imoveisVivaRealElegiveis.await()
            )
        }

    private fun CoroutineScope.filtrarImoveisElegiveis(
        listaImoveisElegivel: ListaImoveisElegivel,
        imoveis: List<ImovelVO>
    ) = async {
        listaImoveisElegivel to imoveis.filter { imovel ->
            listaImoveisElegivel.ehElegivel(imovel)
        }.map { imovel -> imovel.toImove() }
    }
}