package com.kolis.test_catalog_app

import android.app.Application
import com.kolis.test_catalog_app.di.DaggerApplicationComponent

class App : Application() {
    val appComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }
}