package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.ListaImoveisElegivel
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.ListaImoveisUseCase
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.ListaVivaRealElegivel
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.ListaZAPElegivel
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.Imovel
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.viewmodel.ListaImoveisEstados.*
import kotlinx.coroutines.launch

class ListaImoveisViewModel(
    private val userCase: ListaImoveisUseCase = ListaImoveisUseCase()
) : ViewModel() {
    private val state: MutableLiveData<ListaImoveisEstados> = MutableLiveData()
    val viewState: LiveData<ListaImoveisEstados> = state

    fun listarImoveis() {
        state.postValue(Carregamento)
        requisitar {
            userCase.listarImoveis()
        }
    }

    fun listarImoveisPaginacao() {
        state.postValue(CarregamentoPaginacao)
        requisitar {
            userCase.listarImoveisProximaPagina()
        }
    }

    private fun requisitar(func: suspend () -> Map<ListaImoveisElegivel, List<Imovel>>) {
        viewModelScope.launch {
            try {
                val map = func()
                state.postValue(
                    ExibirInformacoes(
                        map[ListaZAPElegivel],
                        map[ListaVivaRealElegivel]
                    )
                )

            } catch (e: Exception) {
                state.postValue(ErroCarregamento)
            }
        }
    }
}