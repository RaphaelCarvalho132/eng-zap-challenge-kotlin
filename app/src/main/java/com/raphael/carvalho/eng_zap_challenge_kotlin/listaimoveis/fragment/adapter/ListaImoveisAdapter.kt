package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.raphael.carvalho.eng_zap_challenge_kotlin.R
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.fragment.ListaImoveisFragment.OnImovelSelecionado
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.Imovel
import com.raphael.carvalho.eng_zap_challenge_kotlin.util.toReais

class ListaImoveisAdapter(
    val listener: OnImovelSelecionado
) : Adapter<ImovelVH>() {
    private val imoveis = ArrayList<Imovel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImovelVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_imovel, parent, false)

        return ImovelVH(view, listener)
    }

    override fun getItemCount() = imoveis.size

    override fun onBindViewHolder(holder: ImovelVH, position: Int) {
        holder.bind(imoveis[position])
    }

    fun addImoveis(imoveis: List<Imovel>) {
        this.imoveis.addAll(imoveis)
        notifyDataSetChanged()
    }
}

class ImovelVH(
    itemView: View,
    listener: OnImovelSelecionado
) : ViewHolder(itemView) {
    private lateinit var imovel: Imovel

    private val tvTipoNegocio = itemView.findViewById<TextView>(R.id.tv_tipo_negocio)
    private val tvValor = itemView.findViewById<TextView>(R.id.tv_valor)
    private val tvQtdQuartos = itemView.findViewById<TextView>(R.id.tv_qtd_quartos)
    private val tvQtdBanheiros = itemView.findViewById<TextView>(R.id.tv_qtd_banheiros)
    private val tvQtdVagas = itemView.findViewById<TextView>(R.id.tv_qtd_vagas)
    private val tvM2 = itemView.findViewById<TextView>(R.id.tv_m2)

    init {
        itemView.setOnClickListener { listener.onImovelSelecionado(imovel) }
    }

    fun bind(imovel: Imovel) {
        this.imovel = imovel

        tvTipoNegocio.context.apply {
            tvTipoNegocio.setText(imovel.nomeNegocio)
            tvValor.text = getString(R.string.valor_imovel_1s, imovel.valorImovel.toReais())

            tvQtdQuartos.text = getString(R.string.qtd_quartos_1d, imovel.qtdQuartos)
            tvQtdBanheiros.text = getString(R.string.qtd_banheiros_1d, imovel.qtdBanheiros)
            tvQtdVagas.text = getString(R.string.qtd_vagas_1d, imovel.qtdVagas)
            tvM2.text = getString(R.string.m2_1d, imovel.areaM2)
        }
    }
}