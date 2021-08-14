package com.kolis.test_catalog_app.ui.utils

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

class DefaultLoadingSpinner(context: Context) : CircularProgressDrawable(context) {
    init {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
}