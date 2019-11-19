package com.raphael.carvalho.eng_zap_challenge_kotlin.util

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import timber.log.Timber

inline fun <reified T : Fragment> FragmentActivity.findFragmentById(@IdRes id: Int): T? {
    val fragment = supportFragmentManager.findFragmentById(id)

    return if (fragment == null || fragment is T) {
        fragment as T?
    } else {
        Timber.tag("ActivityExt")
            .w("Fragment '$fragment' nao corresponde a '${T::class}'")
        null
    }
}
