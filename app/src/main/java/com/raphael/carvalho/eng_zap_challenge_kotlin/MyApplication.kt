package com.raphael.carvalho.eng_zap_challenge_kotlin

import android.app.Application
import timber.log.Timber

/**
 * Inicializacao de bibliotecas
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            TODO("enviar logs para o Crashlytics")
        }
    }
}