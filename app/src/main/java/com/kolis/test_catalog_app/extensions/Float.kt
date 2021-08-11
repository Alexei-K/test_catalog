package com.kolis.test_catalog_app.util

import android.content.Context
import android.util.DisplayMetrics
import java.util.*

fun Float.toDollars(): String {
    return "$ " + String.format(Locale.US, "%.2f", this)
}

fun Float.dpToPx(context: Context): Int {
    return (this * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}