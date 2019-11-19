package com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raphael.carvalho.eng_zap_challenge_kotlin.R
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.fragment.ImovelFragment
import com.raphael.carvalho.eng_zap_challenge_kotlin.listaimoveis.usercase.model.Imovel
import com.raphael.carvalho.eng_zap_challenge_kotlin.util.findFragmentById
import timber.log.Timber

class ImovelActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_IMOVEL = "extraImovel"

        fun configurarExtras(intent: Intent, imovel: Imovel) {
            intent.putExtra(EXTRA_IMOVEL, imovel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imovel)

        val imovel = intent.extras?.getParcelable<Imovel>(EXTRA_IMOVEL)
        if (imovel == null) {
            Timber.tag("Imoveis")
                .w("Argumento nao informado: '$EXTRA_IMOVEL'")
        } else findFragmentById<ImovelFragment>(R.id.f_imovel)?.bind(imovel)
    }
}