package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.raphael.carvalho.eng_zap_challenge_kotlin.R
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.Imovel
import com.raphael.carvalho.eng_zap_challenge_kotlin.util.toReais

class ImovelFragment : Fragment() {
    private lateinit var tvTipoNegocio: TextView
    private lateinit var tvValor: TextView
    private lateinit var tvQtdQuartos: TextView
    private lateinit var tvQtdBanheiros: TextView
    private lateinit var tvQtdVagas: TextView
    private lateinit var tvM2: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_imovel, container, false)
        initViews(view)

        return view
    }

    private fun initViews(view: View) {
        tvTipoNegocio = view.findViewById(R.id.tv_tipo_negocio)
        tvValor = view.findViewById(R.id.tv_valor)
        tvQtdQuartos = view.findViewById(R.id.tv_qtd_quartos)
        tvQtdBanheiros = view.findViewById(R.id.tv_qtd_banheiros)
        tvQtdVagas = view.findViewById(R.id.tv_qtd_vagas)
        tvM2 = view.findViewById(R.id.tv_m2)
    }

    fun bind(imovel: Imovel) {
        tvTipoNegocio.setText(imovel.nomeNegocio)
        tvValor.text = getString(R.string.valor_imovel_1s, imovel.valorImovel.toReais())

        tvQtdQuartos.text = getString(R.string.qtd_quartos_1d, imovel.qtdQuartos)
        tvQtdBanheiros.text = getString(R.string.qtd_banheiros_1d, imovel.qtdBanheiros)
        tvQtdVagas.text = getString(R.string.qtd_vagas_1d, imovel.qtdVagas)
        tvM2.text = getString(R.string.m2_1d, imovel.areaM2)
    }
}
