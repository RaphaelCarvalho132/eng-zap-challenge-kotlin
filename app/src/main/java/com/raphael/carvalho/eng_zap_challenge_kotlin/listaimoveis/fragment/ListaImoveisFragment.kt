package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.raphael.carvalho.eng_zap_challenge_kotlin.R
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.fragment.adapter.ListaImoveisAdapter
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.Imovel
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.viewmodel.ListaImoveisEstados.*
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.viewmodel.ListaImoveisViewModel
import timber.log.Timber

class ListaImoveisFragment : Fragment() {
    companion object {
        private const val ARG_EH_ZAP_IMOVEIS = "argZagImoveis"

        fun newInstancia(ehZAPImoveis: Boolean) =
            ListaImoveisFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_EH_ZAP_IMOVEIS, ehZAPImoveis)
                }
            }
    }

    private lateinit var viewModel: ListaImoveisViewModel
    private lateinit var listener: OnImovelSelecionado
    private var ehZapImoveis: Boolean = true

    private lateinit var adapter: ListaImoveisAdapter

    private lateinit var tvErro: TextView
    private lateinit var pbLoad: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_imoveis, container, false)
        initViews(view)

        if (arguments?.containsKey(ARG_EH_ZAP_IMOVEIS) != true) {
            Timber.tag("ListaImoveis")
                .w("Argumento nao informado: '$ARG_EH_ZAP_IMOVEIS'")
        }
        ehZapImoveis = arguments?.getBoolean(ARG_EH_ZAP_IMOVEIS, true) ?: true

        return view
    }

    private fun initViews(view: View) {
        adapter = ListaImoveisAdapter(listener)
        view.findViewById<RecyclerView>(R.id.rv_lista_imoveis).adapter = adapter

        tvErro = view.findViewById(R.id.tv_erro)
        pbLoad = view.findViewById(R.id.pb_load)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListaImoveisViewModel::class.java)
        viewModel.viewState.observe(viewLifecycleOwner, Observer { movieState ->
            movieState?.also { estado ->
                when (estado) {
                    is Carregamento, CarregamentoPaginacao -> exibirLoad()
                    is ErroCarregamento -> exibirErro()
                    is ExibirInformacoes -> exibirImoveis(
                        if (ehZapImoveis) estado.imoveisZAP
                        else estado.imoveisVivaReal
                    )
                }
            }
        })
        viewModel.listarImoveis()
    }

    private fun exibirImoveis(imoveis: List<Imovel>?) {
        pbLoad.visibility = GONE
        tvErro.visibility = GONE

        imoveis?.also { adapter.addImoveis(imoveis) }
    }

    fun exibirLoad() {
        tvErro.visibility = GONE
        pbLoad.visibility = VISIBLE
    }

    private fun exibirErro() {
        pbLoad.visibility = GONE
        tvErro.visibility = VISIBLE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnImovelSelecionado) {
            listener = context
        } else throw ClassCastException("$context must implement OnImovelSelecionado")
    }

    interface OnImovelSelecionado {
        fun onImovelSelecionado(imovel: Imovel)
    }
}
