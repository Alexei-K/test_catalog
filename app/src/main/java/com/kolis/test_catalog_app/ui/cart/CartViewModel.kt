package com.kolis.test_catalog_app.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kolis.test_catalog_app.data.dress.DressInCartModel
import com.kolis.test_catalog_app.data.dress.DressRepositoryType

class CartViewModel(private val dressRepository: DressRepositoryType) : ViewModel() {

    fun getAllCartItems(): LiveData<List<DressInCartModel>> {
        return dressRepository.dressesInCart()
    }

    fun getTotalCartPrice(): LiveData<Float> {
        return dressRepository.totalCartPrice()
    }
}