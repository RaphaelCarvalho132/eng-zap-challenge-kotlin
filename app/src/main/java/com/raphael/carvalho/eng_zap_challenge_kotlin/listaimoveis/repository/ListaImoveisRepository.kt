package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.repository

import com.raphael.carvalho.eng_zap_challenge_kotlin.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListaImoveisRepository(
    private val service: ListaImoveisService = RetrofitBuilder.new()
) {
    suspend fun listarImoveis(pagina: Int) = withContext(Dispatchers.IO) {
        service.listarImoveis(pagina)
    }
}