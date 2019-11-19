package com.raphael.carvalho.eng_zap_challenge_kotlin.util

import java.math.BigDecimal
import java.text.NumberFormat.getCurrencyInstance

fun BigDecimal.toReais(): String {
    return getCurrencyInstance(BRASIL).format(this)
}