package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.repository

import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.model.ImovelVO
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interface de requisicao para o retrofit
 */
interface ListaImoveisService {
    /**
     * lista os imoveis, presentes na API, da [pagina] informada
     */
    @GET("/sources/source-{pagina}.json")
    suspend fun listarImoveis(@Path("pagina") pagina: Int): List<ImovelVO>
}