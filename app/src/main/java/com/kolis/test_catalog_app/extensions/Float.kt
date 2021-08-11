package com.kolis.test_catalog_app.util

import java.util.*

fun Float.toDollars(): String {
    return "$ " + String.format(Locale.US, "%.2f", this)
}