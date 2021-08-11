package com.kolis.test_catalog_app.ui.home.dressWatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolis.test_catalog_app.data.dress.DressInCartModel
import com.kolis.test_catalog_app.data.dress.DressRepositoryType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WatchDressViewModel( private val dressRepository: DressRepositoryType) : ViewModel() {

    fun addToCart(dressModel: DressInCartModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dressRepository.addDressToCart(dressModel)
            }
        }
    }
}