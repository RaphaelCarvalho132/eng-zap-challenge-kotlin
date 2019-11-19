package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.raphael.carvalho.eng_zap_challenge_kotlin.R
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.activity.adapter.ListasImoveisVPAdapter
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.activity.adapter.ListasImoveisVPAdapter.ListaImoveis
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.fragment.ImovelFragment
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.fragment.ListaImoveisFragment.OnImovelSelecionado
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.Imovel
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.viewmodel.ListaImoveisViewModel
import com.raphael.carvalho.eng_zap_challenge_kotlin.util.findFragmentById

class ListaImoveisActivity : AppCompatActivity(), OnImovelSelecionado {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_imoveis)

        initViews()
        savedInstanceState ?: iniciarRequisicao() // Somente executa a primeira vez
    }

    private fun initViews() {
        findViewById<ViewPager>(R.id.vp_paginas).also { viewPager ->
            findViewById<TabLayout>(R.id.tl_abas).setupWithViewPager(viewPager)

            viewPager.adapter = ListasImoveisVPAdapter(
                supportFragmentManager,
                listOf(
                    ListaImoveis(getString(R.string.tab_zap), true),
                    ListaImoveis(getString(R.string.tab_viva_real), false)
                )
            )
        }
    }

    private fun iniciarRequisicao() {
        ViewModelProviders
            .of(this)
            .get(ListaImoveisViewModel::class.java)
            .listarImoveis()
    }

    override fun onImovelSelecionado(imovel: Imovel) {
        val fragment = findFragmentById<ImovelFragment>(R.id.f_imovel)

        if (fragment == null) {
            val intent = Intent(this, ImovelActivity::class.java)
            ImovelActivity.configurarExtras(intent, imovel)
            startActivity(intent)
        } else fragment.bind(imovel)
    }
}
