package com.kolis.test_catalog_app.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kolis.test_catalog_app.data.dress.DressRepositoryType

class CartViewModelFactory(
    private val repository: DressRepositoryType
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}