package com.kolis.test_catalog_app.ui.home.dressWatch

import androidx.lifecycle.ViewModel
import com.kolis.test_catalog_app.data.dress.DressInCartModel
import com.kolis.test_catalog_app.data.dress.DressRepository
import com.kolis.test_catalog_app.data.dress.DressRepositoryType

class WatchDressViewModel : ViewModel() {

    val dressRepository: DressRepositoryType = DressRepository()
    fun addToCart(dressModel: DressInCartModel) {
        dressRepository.addDressToCart(dressModel)
    }
}