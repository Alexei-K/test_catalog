package com.kolis.test_catalog_app.di

import android.content.Context
import com.kolis.test_catalog_app.MainActivity
import com.kolis.test_catalog_app.data.dress.DressRepositoryType
import com.kolis.test_catalog_app.ui.cart.CartFragment
import com.kolis.test_catalog_app.ui.home.HomeFragment
import com.kolis.test_catalog_app.ui.home.dressWatch.WatchDressFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AbstractModule::class, AllInOneModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(cartFragment: CartFragment)
    fun inject(watchDressFragment: WatchDressFragment)
    fun inject(homeFragment: HomeFragment)

    fun dressRepository(): DressRepositoryType
}