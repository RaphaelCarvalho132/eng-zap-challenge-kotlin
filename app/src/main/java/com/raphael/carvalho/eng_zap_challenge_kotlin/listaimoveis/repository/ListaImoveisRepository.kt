package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.repository

import com.raphael.carvalho.eng_zap_challenge_kotlin.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Define da onde vira as informacoes do imovel
 * @property service servico que sera usado para se comunicar com a API
 */
class ListaImoveisRepository(
    private val service: ListaImoveisService = RetrofitBuilder.new()
) {
    /**
     * lista os imoveis da [pagina] informada
     */
    suspend fun listarImoveis(pagina: Int) = withContext(Dispatchers.IO) {
        service.listarImoveis(pagina)
    }
}