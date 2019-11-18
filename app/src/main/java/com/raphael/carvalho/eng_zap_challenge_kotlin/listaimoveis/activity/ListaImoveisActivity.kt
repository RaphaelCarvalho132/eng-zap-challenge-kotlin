package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raphael.carvalho.eng_zap_challenge_kotlin.R
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.fragment.ListaImoveisFragment.OnImovelSelecionado
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.Imovel

class ListaImoveisActivity : AppCompatActivity(), OnImovelSelecionado {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_imoveis)
    }

    override fun onImovelSelecionado(imovel: Imovel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
