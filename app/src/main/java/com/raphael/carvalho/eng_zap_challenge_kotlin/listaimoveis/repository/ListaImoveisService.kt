package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.repository

import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.model.Imovel
import retrofit2.http.GET
import retrofit2.http.Path

interface ListaImoveisService {
    @GET("/sources/source-{pagina}.json")
    suspend fun listarImoveis(@Path("pagina") pagina: Int): List<Imovel>
}