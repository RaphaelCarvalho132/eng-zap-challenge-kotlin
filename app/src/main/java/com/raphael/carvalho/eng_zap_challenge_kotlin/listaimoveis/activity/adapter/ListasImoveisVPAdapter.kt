package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.activity.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.fragment.ListaImoveisFragment

class ListasImoveisVPAdapter(
    fm: FragmentManager,
    val listasImoveis: List<ListaImoveis>
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = listasImoveis.size

    override fun getItem(position: Int) =
        ListaImoveisFragment.newInstancia(listasImoveis[position].ehZAPImoveis)

    override fun getPageTitle(position: Int): CharSequence? {
        return listasImoveis[position].titulo
    }

    data class ListaImoveis(
        val titulo: String,
        val ehZAPImoveis: Boolean
    )
}