package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase

import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.Imovel
import com.raphael.carvalho.eng_zap_challenge_kotlin.util.InfraestruturaLifeCycleExtensions
import com.raphael.carvalho.eng_zap_challenge_kotlin.util.InfraestruturaLifeCycleExtensions.adicionarRequisicao
import com.raphael.carvalho.eng_zap_challenge_kotlin.util.InfraestruturaLifeCycleExtensions.server
import com.raphael.carvalho.eng_zap_challenge_kotlin.util.Resources.lerArquivo
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.Extensions

/**
 * Testes unitarios do repository de listagem de imoveis
 */
@Extensions(
    ExtendWith(InfraestruturaLifeCycleExtensions::class)
)
class ListaImoveisUseCaseTest {
    companion object {
        private const val ARQ_SIMULA_RESPOSTA = "listarImoveis/resposta2Zap1VivaRealElegivel.json"

        private const val RESULTADO_ZAP_ELEGIVEIS =
            "listarImoveis/resposta2Zap1VivaRealElegivel_resultadoZap.json"
        private const val RESULTADO_VIVA_REAL_ELEGIVEIS =
            "listarImoveis/resposta2Zap1VivaRealElegivel_resultadoVivaReal.json"
    }

    @Test
    fun `listarImoveis - ao listar imoveis, deve retornar imoveis elegiveis pela viva real e zap`() {
        adicionarRequisicao(
            200,
            ARQ_SIMULA_RESPOSTA
        )

        val useCase = ListaImoveisUseCase()
        val mapImoveis = runBlocking { useCase.listarImoveis() }


        assertEquals("/sources/source-1.json", server.takeRequest().path)
        assertEquals(
            mapOf(
                ListaZAPElegivel to lerArquivo<List<Imovel>>(RESULTADO_ZAP_ELEGIVEIS),
                ListaVivaRealElegivel to lerArquivo<List<Imovel>>(RESULTADO_VIVA_REAL_ELEGIVEIS)
            ),
            mapImoveis
        )
    }

    @Test
    fun `listarImoveisProximaPagina - ao paginar imoveis, deve retornar lista filtrada pela viva real e zap`() {
        adicionarRequisicao(200, ARQ_SIMULA_RESPOSTA)
        adicionarRequisicao(200, ARQ_SIMULA_RESPOSTA)

        val useCase = ListaImoveisUseCase()
        val mapImoveis = runBlocking {
            val map = useCase.listarImoveis()
            val mapProximaPagina = useCase.listarImoveisProximaPagina()

            map.juntarRespostas(mapProximaPagina)
        }

        assertEquals("/sources/source-1.json", server.takeRequest().path)
        assertEquals("/sources/source-2.json", server.takeRequest().path)
        assertEquals(
            mapOf(
                ListaZAPElegivel to
                        lerArquivo<List<Imovel>>(RESULTADO_ZAP_ELEGIVEIS).duplicarLista(),
                ListaVivaRealElegivel to
                        lerArquivo<List<Imovel>>(RESULTADO_VIVA_REAL_ELEGIVEIS).duplicarLista()
            ),
            mapImoveis
        )
    }

    @Test
    fun `listarImoveis - ao forcar refresh, deve reiniciar a paginacao`() {
        adicionarRequisicao(200, ARQ_SIMULA_RESPOSTA)
        adicionarRequisicao(200, ARQ_SIMULA_RESPOSTA)
        adicionarRequisicao(200, ARQ_SIMULA_RESPOSTA)

        val useCase = ListaImoveisUseCase()
        runBlocking {
            useCase.listarImoveis()
            useCase.listarImoveisProximaPagina()
            useCase.listarImoveis()
        }

        assertEquals("/sources/source-1.json", server.takeRequest().path)
        assertEquals("/sources/source-2.json", server.takeRequest().path)
        assertEquals("/sources/source-1.json", server.takeRequest().path)
    }
}

private fun <K, V> Map<K, List<V>>.juntarRespostas(map: Map<K, List<V>>): Map<K, List<V>> {
    val hashMap = HashMap<K, List<V>>()

    forEach { key, value ->
        val list = map[key]
        val arrayList = ArrayList<V>(value)
        if (list != null) arrayList.addAll(list)

        hashMap[key] = arrayList
    }

    return hashMap
}

private fun <E> List<E>.duplicarLista(): List<E> {
    val arrayList = ArrayList<E>(this)
    arrayList.addAll(this)

    return arrayList
}