package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.raphael.carvalho.eng_zap_challenge_kotlin.R
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.Imovel
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.viewmodel.ListaImoveisViewModel

class ListaImoveisFragment : Fragment() {
    private lateinit var viewModel: ListaImoveisViewModel
    var listener: OnImovelSelecionado? = null

    private lateinit var tvErro: TextView
    private lateinit var pbLoad: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_imoveis, container, false)
        initViews(view)

        return view
    }

    private fun initViews(view: View) {
        TODO("view.findViewById<RecyclerView>(R.id.rv_lista_imoveis).adapter")

        tvErro = view.findViewById(R.id.tv_erro)
        pbLoad = view.findViewById(R.id.pb_load)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListaImoveisViewModel::class.java)
        viewModel.listarImoveis()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnImovelSelecionado
        if (listener == null) {
            throw ClassCastException("$context must implement OnImovelSelecionado")
        }
    }

    interface OnImovelSelecionado {
        fun onImovelSelecionado(imovel: Imovel)
    }
}
